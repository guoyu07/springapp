package com.app.models.counter;

import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiModelProperty;

public class SearchResponse {
	
	@ApiModelProperty(notes = "The list of all search filter keywords with corresponding counts")
	List<Map<String, Integer>> counts;

	public List<Map<String, Integer>> getCounts() {
		return counts;
	}

	public void setCounts(List<Map<String, Integer>> counts) {
		this.counts = counts;
	}

}
