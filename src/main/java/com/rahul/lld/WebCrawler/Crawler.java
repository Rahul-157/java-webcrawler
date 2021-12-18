package com.rahul.lld.WebCrawler;

import com.rahul.lld.SearchEngine.Google;
import com.rahul.lld.SearchEngine.SearchEngine;
import com.rahul.lld.Utils.Constants;
import com.rahul.lld.Utils.Log;
import com.rahul.lld.Utils.SharedQueue;
import com.rahul.lld.Utils.UrlUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;

public class Crawler {
    private static Logger log= Log.LOGGER;
    private ArrayList<Thread> spiders;
    private final SharedQueue sharedQueue = SharedQueue.getInstance();
    private final UrlUtils urlUtils = new UrlUtils();

    public Crawler() {
        this.spiders = new ArrayList<>(Constants.NUM_SPIDERS);
        for (int i = 1; i <= Constants.NUM_SPIDERS; i++) {
            Thread spiderThread = new Thread(new WebSpider());
            spiderThread.setName("Spider-" + i);
            spiders.add(spiderThread);
        }
    }

    private Set<SearchEngine> getSearchEngines(){
        HashSet<String> providedSearchEngines = Constants.SEARCH_ENGINES;
        Set<SearchEngine> searchEngines = new HashSet<>();
        Set<String> supportedAndProvidedSearchEngines = Constants.SUPPORTED_SEARCH_ENGINES.keySet();
        supportedAndProvidedSearchEngines.retainAll(providedSearchEngines);
        supportedAndProvidedSearchEngines.forEach(searchEngine->{
            searchEngines.add(Constants.SUPPORTED_SEARCH_ENGINES.get(searchEngine));
        });
        return searchEngines;
    }

    private void releaseSpiders(Set<URL> urls){
        urls.forEach(url -> sharedQueue.put(url,1));
        log.info("Spiders Released");
        this.spiders.forEach(Thread::start);
    }

    public void startCrawling(){
            Set<URL> allURLs = new HashSet<>();
            Set<SearchEngine> searchEngines =getSearchEngines();
            searchEngines.forEach(engine ->{
                String query;
                try(BufferedReader bufferedReader = new BufferedReader(new FileReader(Constants.INITIAL_QUERIES_FILE))) {
                    while ((query = bufferedReader.readLine()) != null) {
                        allURLs.addAll(engine.searchQuery(query));
                    }
                }catch(IOException e){
                    log.severe("Provide correct path for you queries file");
                    throw new RuntimeException(e);
                }
            });
            this.releaseSpiders(allURLs);
    }
}
