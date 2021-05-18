package com.lms.app.service.impl;

import java.io.ByteArrayInputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
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
	public ByteArrayInputStream findLeadByDate(long from, long to, Integer id) {
		Timestamp fromStamp = new Timestamp(from);
		Date fromDate = new Date(fromStamp.getTime());
		Timestamp toStamp = new Timestamp(to);
		Date toDate = new Date(toStamp.getTime());
		if (id ==null) {
			List<Lead> findLeadByDate = downloadLeadCsvRepository.findLeadByDate(fromDate, toDate);
			return CsvUtils.leadToCsv(findLeadByDate);
		} else {
			List<Lead> findAllByDateAndId = downloadLeadCsvRepository.findAllByDateAndId(fromDate, toDate, id);
			return CsvUtils.leadToCsv(findAllByDateAndId);
		}

	}

}
