package com.lms.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lms.app.to.LeadViewTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lms.app.service.ILeadService;
import com.lms.app.to.LeadTo;
import com.lms.app.to.RequestTo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/v2")
@Api("LMS Save Lead Data And Get Reports APIs")
public class LeadController {
	private ILeadService iLeadService;

	@Autowired
	public LeadController(ILeadService iLeadService) {
		this.iLeadService = iLeadService;
	}

	@PostMapping("/lead/data")
	@ApiOperation(value = "saveLeadData", notes = "API to Save Lead Data")
	public ResponseEntity<?> saveLeadData(
			@ApiParam(value = "Provide the Lead data to save Lead", required = true) @RequestBody LeadTo leadTo) {
		return ResponseEntity.ok(iLeadService.saveLead(leadTo));
	}

	@PostMapping("/reports")
	@ApiOperation(value = "ViewLeadReports", notes = "API to get Reports Between given Dates or Between dates and Appuser. Pagination Enabled")
	public ResponseEntity<?> findAllLeadByDate(
			@ApiParam(value = "Reports can be fetched by entering From Date to To Date"
					+ " or From Date to To Date and AppUser Id. For Pagination Start Page No. and No. of "
					+ "Data should be given to fetch.", required = true) @RequestBody RequestTo request) {
		Long from = request.getFromDate();
		Long to = request.getToDate();
		Integer id = request.getId();
		String city = request.getCity();
		if (from == null || to == null || from == null && to == null) {
			Map<String, String> map = new HashMap<>();
			map.put("message", "From date or To date is not given.");
			return ResponseEntity.badRequest().body(map);
		}

		if (request.getStartPage() < 1) {
			Map<String, String> map = new HashMap<>();
			map.put("message", "Input page number is not valid");
			return ResponseEntity.badRequest().body(map);
		}
		Pageable pageble = PageRequest.of(request.getStartPage() - 1, request.getNoOfData());
		List<LeadViewTo> findAllByDate = iLeadService.findAllLeadReports(from, to, id,city, pageble);
		return ResponseEntity.ok(findAllByDate);
	}

	@PostMapping("/reports/count")
	@ApiOperation(value = "getCountOfLeadReports", notes = "API to get Count of Lead Reports.")
	public ResponseEntity<?> findNumberOfLeadReports(
			@ApiParam(value = "Count of Lead Reports can be fetched by Dates or by Dates and AppUser id.", required = true) @RequestBody RequestTo request) {
		Long from = request.getFromDate();
		Long to = request.getToDate();
		Integer id = request.getId();
		if (from == null || to == null || from == null && to == null) {
			Map<String, String> map = new HashMap<>();
			map.put("message", "From date or To date is not given.");
			return ResponseEntity.badRequest().body(map);
		}
		Integer findNumberOfLeadReports = iLeadService.findNumberOfLeadReports(from, to, id);
		Map<String, Integer> m = new HashMap<>();
		m.put("count", findNumberOfLeadReports);
		return ResponseEntity.ok(m);
	}

}
