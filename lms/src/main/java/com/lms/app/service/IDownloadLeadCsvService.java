package com.lms.app.service;

import java.io.ByteArrayInputStream;

public interface IDownloadLeadCsvService {
	public ByteArrayInputStream findLeadByDate(long from, long to,Integer id);

}
