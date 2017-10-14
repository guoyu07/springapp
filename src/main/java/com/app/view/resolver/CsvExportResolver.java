package com.app.view.resolver;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import com.app.view.CsvView;

import java.util.Locale;

public class CsvExportResolver implements ViewResolver {
	
	@Override
	public View resolveViewName(String s, Locale locale) throws Exception {
        return new CsvView();
	}
}
