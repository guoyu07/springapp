package com.app.controller.counter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.models.counter.SearchRequest;
import com.app.models.counter.SearchResponse;
import com.app.service.counter.FileCounterService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/counter-api")
@Api(tags= {"FileCounter"}, description="Rest Service to obtain word counts on file")
public class CounterRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(CounterRestController.class);

    @Autowired
    private FileCounterService counterService;
    
    @ApiOperation(value = "Provides a view of search parameters passed in the request with associated recurrence count", response = SearchResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list containing the counts"),
            @ApiResponse(code = 500, message = "The server encountered error. Please contact administrator!")
    })
    @PostMapping(value="/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SearchResponse findWordCount(@ApiParam(value = "List of strings, search params", required = true) @RequestBody SearchRequest searchRequest) throws IOException {
    	logger.debug("Request Params {}", searchRequest.getSearchText().toString());
    	Map<String, Integer> wordMap = counterService.findCountForParameters(searchRequest.getSearchText());
    	return transformToSearchResponse(wordMap);
    }
    
    private SearchResponse transformToSearchResponse(Map<String, Integer> wordMap) {
    	SearchResponse response = new SearchResponse();
    	response.setCounts(wordMap.entrySet().stream().map(entry -> 
			new HashMap<String, Integer>() {{
				put(entry.getKey(), entry.getValue()); 
			}}
    	).collect(Collectors.toList()));
		return response;
    }
}
