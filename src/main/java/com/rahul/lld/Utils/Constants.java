package com.rahul.lld.Utils;

import com.rahul.lld.SearchEngine.Google;
import com.rahul.lld.SearchEngine.SearchEngine;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class  Constants {
    public static final int MAX_RETRIES_WATCH_QUEUE =2;    // If URL queue empty then watch queue at least 5 times
    public static final int SLEEP_BETWEEN_RETRIES = 10000;  // Time sleep between each watch in MilliSeconds
    public static final Map<String, SearchEngine> SUPPORTED_SEARCH_ENGINES = Stream.of(new Object[][] {
            { "google", new Google() } // add more supported search engines in future
    }).collect(Collectors.toMap(data -> (String) data[0], data -> (SearchEngine) data[1]));
    public static final String RESULTS_CSV = "./results.csv";

    public static int NUM_SPIDERS = 10;
    public static int MAX_LEVEL = 3;
    public static HashSet<String> SEARCH_ENGINES = new HashSet<String>(Arrays.asList("google"));
    public static String INITIAL_QUERIES_FILE = "./queries.txt";
    public static String REGEX_TO_EXTRACT = "(\\+\\d{1,2}\\s?)?1?\\-?\\.?\\s?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}";


    static{
        String configFile = System.getProperty("configFile");
        try {
            JSONObject configJSON = FileUtils.getInstance().readJSON(configFile);
            if(configJSON.get("NUM_SPIDERS")!=null)
                NUM_SPIDERS = Integer.valueOf(configJSON.get("NUM_SPIDERS").toString());
            if(configJSON.get("MAX_LEVEL")!=null)
                MAX_LEVEL = Integer.valueOf(configJSON.get("MAX_LEVEL").toString());
            if(configJSON.get("REGEX_TO_EXTRACT")!=null)
                REGEX_TO_EXTRACT = configJSON.get("REGEX_TO_EXTRACT").toString();
            if(configJSON.get("INITIAL_QUERIES_FILE")!=null)
                INITIAL_QUERIES_FILE = configJSON.get("INITIAL_QUERIES_FILE").toString();
            if(configJSON.get("SEARCH_ENGINES")!=null)
                SEARCH_ENGINES = new HashSet<>(Arrays.asList(configJSON.get("SEARCH_ENGINES").toString().split(",")));
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
