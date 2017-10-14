package com.app.service.counter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface FileCounterService {

	public Map<String, Integer> findCountForParameters(List<String> searchVariables) throws IOException;
	public Map<String, Integer> getTopFileResults(Integer count) throws IOException;
}
