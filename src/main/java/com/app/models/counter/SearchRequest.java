package com.app.models.counter;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class SearchRequest {
	
	@ApiModelProperty(notes = "The list of all search filter keywords")
	List<String> searchText;

	public List<String> getSearchText() {
		return searchText;
	}

	public void setSearchText(List<String> searchText) {
		this.searchText = searchText;
	}
	
}
