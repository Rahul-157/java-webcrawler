package com.rahul.lld.Utils;

public abstract class  Constants {
    public static final int NUM_SPIDERS = 10;
    public static final int MAX_RETRIES_WATCH_QUEUE =2;    // If URL queue empty then watch queue at least 5 times
    public static final int SLEEP_BETWEEN_RETRIES = 10000;  // Time sleep between each watch in MilliSeconds
    public static final int MAX_LEVEL = 2;
    public static final String D_10 = "(\\+\\d{1,3}[- ]?)?\\d{10}";
    public static final String SEARCH_ENGINE_QUERY = "https://www.google.com/search?q=%s";
    public static final String INITIAL_QUERIES_FILE = "./queries.txt";
    public static final String RESULTS_CSV = "./results.csv";
}
