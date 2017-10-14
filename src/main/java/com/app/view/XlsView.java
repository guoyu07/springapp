package com.app.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractXlsView;

public class XlsView extends AbstractXlsView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Content-Disposition", "attachment; filename=\"xls-file.xls\"");
		Map<String, Object> map = (Map<String, Object>) model.get("exportData");
		Sheet sheet = workbook.createSheet();
		int rowCount = 0;
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			Row row = sheet.createRow(rowCount++);
			row.createCell(0).setCellValue(entry.getKey());
			row.createCell(1).setCellValue(String.valueOf(entry.getValue()));
		}
	}

}
