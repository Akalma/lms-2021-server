package com.lms.app.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.lms.app.to.LeadTo;
import org.springframework.data.repository.query.Param;

public interface ILeadService {
	public LeadTo saveLead(LeadTo userHome);

	public List<LeadTo> findAllLeadReports(long from, long to, Integer id, String city, Pageable pageable);
	
	public Integer findNumberOfLeadReports(long from,long to,Integer id);
}
