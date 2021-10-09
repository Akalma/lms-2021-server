package com.lms.app.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.lms.app.entity.AppUsers;
import com.lms.app.entity.LeadView;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import com.lms.app.entity.Lead;

public class CsvUtils {
	private static final String[] headers = { "Id", "First Name", "Last Name", "Mobile", "Area", "Existing Broadband",
			"Lead Type", "Added By", "Created Date", "Remarks","OBMR_ID","city" };

	/**
	 * Converts List Of Lead reports into CSV File.
	 * 
	 * @param lead of {@link Lead}
	 * @return {@link ByteArrayInputStream}
	 */
	public static ByteArrayInputStream leadToCsv(List<LeadView> lead) {
		final CSVFormat format = CSVFormat.DEFAULT.withHeader(headers);
		try (ByteArrayOutputStream out = new ByteArrayOutputStream();
				CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
			for (LeadView leads : lead) {
				List<Serializable> data = Arrays.asList(String.valueOf(leads.getId()), leads.getFirstName(),
						leads.getLastName(), "=\""+leads.getMobile()+"\"", "'#9, 5th main, 18th cross, BTM 2nd stage-560076", leads.getExistingBroadband(),
						leads.getLeadType(), Optional.ofNullable(leads.getAppUsers()).map(AppUsers::getName).orElse(""),
						"=\""+leads.getDate()+"\"", leads.getRemarks(),"=\""+leads.getOBMRID()+"\"",leads.getCity());
				csvPrinter.printRecord(data);
			}
			csvPrinter.flush();
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("failed to import data to CSV file: " + e.getMessage());
		}
	}
}
