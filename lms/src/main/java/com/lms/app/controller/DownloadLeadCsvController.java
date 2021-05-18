package com.lms.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.app.service.IDownloadLeadCsvService;
import com.lms.app.to.RequestTo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/v2")
@Api("LMS Lead Download Reports API")
public class DownloadLeadCsvController {
	private IDownloadLeadCsvService iDownloadLeadCsvService;

	@Autowired
	public DownloadLeadCsvController(IDownloadLeadCsvService iDownoadLeadCsvService) {
		this.iDownloadLeadCsvService = iDownoadLeadCsvService;

	}

	@PostMapping("/download/reports")
	@ApiOperation(value = "downloadReport", notes = "API to Download Reports Based On Dates or Between dates and AppUser.")
	public ResponseEntity<?> downloadLeadCsvDate(
			@ApiParam(value = "Lead reports can be downloaded by providing From date to To date"
					+ " Or From date to To date and AppUser Id. Provide request accordingly. ") @RequestBody RequestTo request) {
		Long from = request.getFromDate();
		Long to = request.getToDate();
		Integer id = request.getId();
		if (from == null || to == null || from == null && to == null) {
			Map<String, String> map = new HashMap<>();
			map.put("message", "From date or To date is not given.");
			return ResponseEntity.badRequest().body(map);
		}
		String fileName = "lead.csv";
		InputStreamResource file = new InputStreamResource(iDownloadLeadCsvService.findLeadByDate(from, to, id));
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
				.contentType(org.springframework.http.MediaType.parseMediaType("application/csv")).body(file);

	}
}
