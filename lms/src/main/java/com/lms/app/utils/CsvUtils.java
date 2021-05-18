package com.lms.app.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import com.lms.app.entity.Lead;

public class CsvUtils {
	private static final String[] headers = { "Id", "First Name", "Last Name", "Mobile", "Area", "Existing Broadband",
			"Lead Type", "Added By", "Created Date", "Remarks" };

	/**
	 * Converts List Of Lead reports into CSV File.
	 * 
	 * @param List of {@link Lead}
	 * @return {@link ByteArrayInputStream}
	 */
	public static ByteArrayInputStream leadToCsv(List<Lead> lead) {
		final CSVFormat format = CSVFormat.DEFAULT.withHeader(headers);
		try (ByteArrayOutputStream out = new ByteArrayOutputStream();
				CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
			for (Lead leads : lead) {
				List<Serializable> data = Arrays.asList(String.valueOf(leads.getId()), leads.getFirstName(),
						leads.getLastName(), leads.getMobile(), leads.getArea(), leads.getExistingBroadband(),
						leads.getLeadType(), leads.getAppUsers().getId(), leads.getDate(), leads.getRemarks());
				csvPrinter.printRecord(data);
			}
			csvPrinter.flush();
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("failed to import data to CSV file: " + e.getMessage());
		}
	}
}
