package com.rahul.lld.WebCrawler;


import com.rahul.lld.Utils.*;
import javafx.util.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebSpider implements Runnable{
    private final static SharedQueue sharedQueue = SharedQueue.getInstance();
    private final UrlUtils urlUtils = new UrlUtils();
    private final static FileUtils fileUtils = FileUtils.getInstance();
    private static Logger log= Log.LOGGER;
    private URL urlToProcess;
    private Integer currentUrlLevel;
    private final Pattern p = Pattern.compile(Constants.D_10);

    void processPage(String res){
        Matcher m = p.matcher(res);
        StringBuilder results = new StringBuilder();
        while(m.find()){
            results.append(m.group()).append("\n");
        }
        fileUtils.write(results.toString());
        Document document = Jsoup.parse(res);
        Elements elements = document.getElementsByTag("a");
        elements.forEach(element -> {
            String href = element.attr("href");
            try {
                if(urlUtils.isValidUrl(href) && !sharedQueue.alreadyVisited(href))
                    sharedQueue.put(new URL(href),currentUrlLevel+1);
                else if(urlUtils.isValidPath(href)){
                    URL hostWithPath = new URL(urlToProcess.getProtocol(),urlToProcess.getHost(),urlUtils.encodeURL(href));
                    if(!sharedQueue.alreadyVisited(hostWithPath.toString()))
                        sharedQueue.put(hostWithPath,currentUrlLevel+1);
                }
            } catch (MalformedURLException e) {

                log.severe(e.getMessage());
            }
        });
    }

    public void processCurrentURL()  {
        if (this.currentUrlLevel > Constants.MAX_LEVEL)
            return;
        if(sharedQueue.alreadyVisited(urlToProcess))
            return;
        try {
            log.info(String.format("Processing URL : %s",urlToProcess));
            String response = new GetRequest().get(urlToProcess);
            processPage(response);
        } catch (IOException  e){
            log.severe(e.getMessage());
        }
    }

    @Override
    public void run() {
        int retries=Constants.MAX_RETRIES_WATCH_QUEUE;
        try {
            while (retries > 0) {
                while (!sharedQueue.empty()) {
                    Pair<URL, Integer> extractedUrl = sharedQueue.get();
                    this.urlToProcess = extractedUrl.getKey();
                    this.currentUrlLevel = extractedUrl.getValue();
                    this.processCurrentURL();
                }
                log.info(String.format("%s observed URL Queue Empty. Countdown : %d",Thread.currentThread().getName(),retries));
                Thread.sleep(Constants.SLEEP_BETWEEN_RETRIES);
                retries--;
            }
        } catch (InterruptedException  e){
            log.severe(e.getMessage());
            log.severe(String.format("%s died.",Thread.currentThread().getName()));
        }
    }
}
