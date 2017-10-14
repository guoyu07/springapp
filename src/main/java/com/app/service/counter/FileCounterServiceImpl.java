package com.app.service.counter;

import com.app.dao.counter.CounterDao;
import com.app.entity.FileEntity;
import com.app.service.counter.FileCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileCounterServiceImpl implements FileCounterService {
	
	private final static Pattern pattern = Pattern.compile("[\\W]+");

    @Autowired
    @Qualifier("counterDao")
    private CounterDao counterDao;

    @Override
    public Map<String, Integer> findCountForParameters(List<String> searchVariables) throws IOException {
    	FileEntity textFile = counterDao.fetchFile();
    	Map<String, Integer> wordCountMap = new TreeMap<String, Integer>(String.CASE_INSENSITIVE_ORDER);
    	searchVariables.stream().forEach(searchVariable -> wordCountMap.put(searchVariable, 0));
    	
    	//Scan each line filter search words and add the word count and associate with search variables at once. 
    	try(Scanner sc = new Scanner(textFile.getFileText())) {
    		while(sc.hasNextLine()) {
    			Stream<String> wordStream = pattern.splitAsStream(sc.nextLine());
    			if(searchVariables.size() > 0) {
    				wordStream = wordStream.filter(wordCountMap::containsKey);
    			}
    			wordStream.forEach(word -> wordCountMap.put(word, Optional.ofNullable(wordCountMap.get(word)).orElse(new Integer(0)) + 1));
    		}
    	}
		return wordCountMap;
    }

	@Override
	public Map<String, Integer> getTopFileResults(Integer count) throws IOException {
		Set<Map.Entry<String, Integer>> set = findCountForParameters(new ArrayList<String>()).entrySet();
		ArrayList<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(set);
		Collections.sort(list,  
				(Entry<String, Integer> o1, Entry<String, Integer> o2) -> o2.getValue().compareTo(o1.getValue()));
		return list.stream().limit(count).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o, n) -> n, LinkedHashMap::new));
	}
}
