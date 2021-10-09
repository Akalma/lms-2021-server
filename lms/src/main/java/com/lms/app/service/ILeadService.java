package com.lms.app.service;

import java.util.List;

import com.lms.app.to.LeadViewTo;
import org.springframework.data.domain.Pageable;
import com.lms.app.to.LeadTo;
import org.springframework.data.repository.query.Param;

public interface ILeadService {
	public LeadTo saveLead(LeadTo userHome);

	public List<LeadViewTo> findAllLeadReports(long from, long to, Integer id, String city, Pageable pageable);
	
	public Integer findNumberOfLeadReports(String city,long from,long to,Integer id);
}
