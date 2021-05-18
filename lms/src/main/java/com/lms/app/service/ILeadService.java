package com.lms.app.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.lms.app.to.LeadTo;

public interface ILeadService {
	public LeadTo saveLead(LeadTo userHome);

	public List<LeadTo> findAllLeadReports(long from, long to, Integer id, Pageable pageable);
	
	public Integer findNumberOfLeadReports(long from,long to,Integer id);
}
