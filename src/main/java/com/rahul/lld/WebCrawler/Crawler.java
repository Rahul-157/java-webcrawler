package com.rahul.lld.WebCrawler;

import com.rahul.lld.Utils.Constants;
import com.rahul.lld.Utils.Log;
import com.rahul.lld.Utils.SharedQueue;
import com.rahul.lld.Utils.UrlUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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

    private void releaseSpiders(){
        this.spiders.forEach(Thread::start);
    }

    public void startCrawling(){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(Constants.INITIAL_QUERIES_FILE))){
            String query;
            while ((query = bufferedReader.readLine()) != null)
                sharedQueue.put(new URL(String.format(Constants.SEARCH_ENGINE_QUERY,urlUtils.encodeURL(query))),1);
            this.releaseSpiders();
        }catch(IOException e){
            log.severe("Write Your Initial Queries in individual lines into queries.txt in root folder of project.");
        }
    }
}
