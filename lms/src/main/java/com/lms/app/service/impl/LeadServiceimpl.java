package com.lms.app.service.impl;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import com.lms.app.entity.LeadView;
import com.lms.app.repository.LeadViewRepository;
import com.lms.app.to.AppUsersTo;
import com.lms.app.to.LeadViewTo;
import org.apache.commons.collections.CollectionUtils;

import com.lms.app.value.InvalidPayloadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.lms.app.entity.AppUsers;
import com.lms.app.entity.Lead;
import com.lms.app.repository.AppUsersRepository;
import com.lms.app.repository.LeadRepository;
import com.lms.app.service.ILeadService;
import com.lms.app.to.LeadTo;
import com.lms.app.utils.DozerUtils;
import com.lms.app.value.PageNotAvailableException;


@Service
public class LeadServiceimpl implements ILeadService {

	private LeadRepository leadRepository;
	private AppUsersRepository appRepository;
	private DozerUtils dozerUtils;
	private LeadViewRepository leadViewRepository;

	@Autowired
	public LeadServiceimpl(LeadRepository leadRepository, DozerUtils dozerUtils, AppUsersRepository appRepository,
						   LeadViewRepository leadViewRepository) {
		this.leadRepository = leadRepository;
		this.dozerUtils = dozerUtils;
		this.appRepository = appRepository;
		this.leadViewRepository=leadViewRepository;
	}

	/**
	 * Saves Lead data
	 * 
	 * @param leadTo Lead Data
	 * @return {@link LeadTo}
	 */
	@Override
	public LeadTo saveLead(LeadTo leadTo) {
		if (leadTo.getAddedBy()==null) throw new InvalidPayloadException("must provide added by value.");
		long addedBy = Long.parseLong(leadTo.getAddedBy());
		AppUsers findById = appRepository.findAppUsersById(addedBy);
		Lead lead = dozerUtils.convert(leadTo, Lead.class);
		lead.setDate(new Date());
		lead.setAppUsers(findById);
		Lead saved = leadRepository.save(lead);
		Long id = saved.getAppUsers().getId();
		String addedByTo=""+id;
		leadTo.setId(saved.getId());
		leadTo.setDate(saved.getDate().getTime());
		leadTo.setAddedBy(addedByTo);
		return leadTo;
	}

	/**
	 * Finds Lead reports according to given dates input or according to given dates
	 * and AppUser id input.
	 * 
	 * @param from From Date
	 * @param to   To Date
	 * @param id   appUser Id
	 * @return List of {@link LeadTo}
	 */
	@Override
	public List<LeadViewTo> findAllLeadReports(long from, long to, Integer id,String city, Pageable pageable) {
		Date fromDate = new Date(from);
		Date toDate = new Date(to);
		List<LeadView> leadForReports=new ArrayList<>();
		if (id == null) {
			// finding all records
			Integer totalLeadCount = leadViewRepository.findNumberOfReportsByDate(fromDate, toDate);
			int pageSize = pageable.getPageSize();
			int pageNumber = pageable.getPageNumber();
			int totalPage = (totalLeadCount / pageSize);
			if (pageNumber > totalPage)
				throw new PageNotAvailableException();
			else {
				leadForReports = Optional.ofNullable(leadViewRepository.findAllByDate(fromDate, toDate,city, pageable))
						.filter(CollectionUtils::isNotEmpty).orElse(Collections.EMPTY_LIST);
			}
		} else {
			leadForReports = Optional.ofNullable(leadViewRepository.findAllByDateAndId(fromDate, toDate, id,city, pageable))
					.filter(CollectionUtils::isNotEmpty).orElse(Collections.EMPTY_LIST);
		}

		return (List<LeadViewTo>) leadForReports.stream().map(lt -> {
			LeadViewTo leadTo = dozerUtils.convert(lt, LeadViewTo.class);
			Long appUserId = Optional.ofNullable(lt.getAppUsers()).map(AppUsers::getId).orElse(1L);
			String appUserIdTo=""+appUserId;
			leadTo.setAddedBy(appUserIdTo);
			leadTo.setAppUsersTo(Optional.ofNullable(lt.getAppUsers())
					.map(appuser->dozerUtils.convert(appuser, AppUsersTo.class)).orElse(null));
			return leadTo;
		}).collect(Collectors.toList());

	}

	/**
	 * Finds count of Lead reports between dates or dates and AppUser id.
	 * 
	 * @param from From Date
	 * @param to   To Date
	 * @param id   AppUser Id
	 */
	@Override
	public Integer findNumberOfLeadReports(long from, long to, Integer id) {
		Date fromDate = new Date(from);
		Date toDate = new Date(to);

		if (null == id) {
			return leadViewRepository.findNumberOfReportsByDate(fromDate, toDate);
		} else {
			return leadViewRepository.findNumberOfReportsByDateAndId(fromDate, toDate, id);
		}
	}

}
