package com.lms.app.service.impl;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import com.lms.app.to.AppUsersTo;
import org.apache.commons.collections.CollectionUtils;
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

	@Autowired
	public LeadServiceimpl(LeadRepository leadRepository, DozerUtils dozerUtils, AppUsersRepository appRepository) {
		this.leadRepository = leadRepository;
		this.dozerUtils = dozerUtils;
		this.appRepository = appRepository;
	}

	/**
	 * Saves Lead data
	 * 
	 * @param leadTo Lead Data
	 * @return {@link LeadTo}
	 */
	@Override
	public LeadTo saveLead(LeadTo leadTo) {
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
	public List<LeadTo> findAllLeadReports(long from, long to, Integer id, Pageable pageable) {
		Date fromDate = new Date(from);
		Date toDate = new Date(to);
		List<Lead> leadForReports=new ArrayList<>();
		if (id == null) {
			// finding all records
			Integer totalLeadCount = leadRepository.findNumberOfReportsByDate(fromDate, toDate);
			int pageSize = pageable.getPageSize();
			int pageNumber = pageable.getPageNumber();
			int totalPage = (totalLeadCount / pageSize);
			if (pageNumber > totalPage)
				throw new PageNotAvailableException();
			else {
				leadForReports = Optional.ofNullable(leadRepository.findAllByDate(fromDate, toDate, pageable))
						.filter(CollectionUtils::isNotEmpty).orElse(Collections.EMPTY_LIST);
			}
		} else {
			leadForReports = Optional.ofNullable(leadRepository.findAllByDateAndId(fromDate, toDate, id, pageable))
					.filter(CollectionUtils::isNotEmpty).orElse(Collections.EMPTY_LIST);
		}

		return (List<LeadTo>) leadForReports.stream().map(lt -> {
			LeadTo leadTo = dozerUtils.convert(lt, LeadTo.class);
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
			return leadRepository.findNumberOfReportsByDate(fromDate, toDate);
		} else {
			return leadRepository.findNumberOfReportsByDateAndId(fromDate, toDate, id);
		}
	}

}
