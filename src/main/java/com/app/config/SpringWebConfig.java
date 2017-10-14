package com.app.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

import com.app.view.resolver.CsvExportResolver;
import com.app.view.resolver.XlsExportResolver;

@Configuration
public class SpringWebConfig extends WebMvcConfigurerAdapter {

	@Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON).favorPathExtension(true);
	}
	
	/*
     * Configure ContentNegotiatingViewResolver
     */
    @Bean
    public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setContentNegotiationManager(manager);

        // Define export mechanisms possible view resolvers
        List<ViewResolver> resolvers = new ArrayList<>();
        resolvers.add(csvViewResolver());
        resolvers.add(xlsViewResolver());
        resolver.setViewResolvers(resolvers);
        return resolver;
    }
    
    @Bean
    public ViewResolver csvViewResolver() {
        return new CsvExportResolver();
    }
    
    @Bean
    public ViewResolver xlsViewResolver() {
        return new XlsExportResolver();
    }
}
