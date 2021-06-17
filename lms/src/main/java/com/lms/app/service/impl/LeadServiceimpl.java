package com.lms.app.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
	public List<LeadTo> findAllLeadReports(long from, long to, Integer id, Pageable pageable) {
		Timestamp fromStamp = new Timestamp(from);
		Date fromDate = new Date(fromStamp.getTime());
		Timestamp toStamp = new Timestamp(to);
		Date toDate = new Date(toStamp.getTime());
		if (id == null) {
			Integer totalLeadCount = leadRepository.findNumberOfReportsByDate(fromDate, toDate);
			int pageSize = pageable.getPageSize();
			int pageNumber = pageable.getPageNumber();
			int totalPage = (totalLeadCount / pageSize);
			if (pageNumber > totalPage)
				throw new PageNotAvailableException();
			else {
				List<Lead> findAllByDate = leadRepository.findAllByDate(fromDate, toDate, pageable);
				return findAllByDate.stream().map(lt -> {
					LeadTo leadTo = dozerUtils.convert(lt, LeadTo.class);
					if (lt.getAppUsers()!=null)leadTo.setAddedBy(""+lt.getAppUsers().getId());
					return leadTo;
				}).collect(Collectors.toList());
			}
		} else {
			List<Lead> findAllByDateAndId = leadRepository.findAllByDateAndId(fromDate, toDate, id, pageable);
			return findAllByDateAndId.stream().map(lt -> {
				LeadTo leadTo = dozerUtils.convert(lt, LeadTo.class);
				if(lt.getAppUsers().getId()!=null) leadTo.setAddedBy(""+lt.getAppUsers().getId());
				return leadTo;
			}).collect(Collectors.toList());
		}
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
		Timestamp fromStamp = new Timestamp(from);
		Date fromDate = new Date(fromStamp.getTime());
		Timestamp toStamp = new Timestamp(to);
		Date toDate = new Date(toStamp.getTime());

		if (id == null) {
			int count = leadRepository.findNumberOfReportsByDate(fromDate, toDate);
			return count;
		} else {
			int count = leadRepository.findNumberOfReportsByDateAndId(fromDate, toDate, id);
			return count;
		}
	}

}
