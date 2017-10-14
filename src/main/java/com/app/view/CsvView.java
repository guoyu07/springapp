package com.app.view;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;

public class CsvView extends AbstractCsvView {
	
	private static final CsvPreference PIPE_DELIMITED = new CsvPreference.Builder('"', '|', "\n").build(); 

	@Override
	protected void buildCsvDocument(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Content-Disposition", "attachment; filename=\"csv-file.csv\"");
		try (ICsvListWriter csvWriter = new CsvListWriter(response.getWriter(), PIPE_DELIMITED)) {
			Map<String, Object> map = (Map<String, Object>) model.get("exportData");
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				csvWriter.write(entry.getKey(), String.valueOf(entry.getValue()));
			}
		}
	}

}
