package com.lms.app.service.impl;

import java.io.ByteArrayInputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.lms.app.entity.LeadView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lms.app.entity.Lead;
import com.lms.app.repository.DownloadLeadCsvRepository;
import com.lms.app.service.IDownloadLeadCsvService;
import com.lms.app.utils.CsvUtils;

@Service
public class DownloadLeadCsvServiceImpl implements IDownloadLeadCsvService {

	private DownloadLeadCsvRepository downloadLeadCsvRepository;

	@Autowired
	public DownloadLeadCsvServiceImpl(DownloadLeadCsvRepository downloadLeadCsvRepository) {
		this.downloadLeadCsvRepository = downloadLeadCsvRepository;
	}

	@Override
	public ByteArrayInputStream findLeadByDate(long from, long to, Integer id,String city) {
		Date fromDate = new Date(from);
		Date toDate = new Date(to);
		if (id ==null) {
			List<LeadView> findLeadByDate = downloadLeadCsvRepository.findLeadByDate(fromDate, toDate,city);
			return CsvUtils.leadToCsv(findLeadByDate);
		} else {
			List<LeadView> findAllByDateAndId = downloadLeadCsvRepository.findAllByDateAndId(fromDate, toDate, id,city);
			return CsvUtils.leadToCsv(findAllByDateAndId);
		}

	}

}
