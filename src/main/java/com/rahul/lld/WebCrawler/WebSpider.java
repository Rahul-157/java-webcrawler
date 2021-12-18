package com.rahul.lld.WebCrawler;


import com.rahul.lld.Utils.*;
import javafx.util.Pair;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebSpider extends Spider implements Runnable{

    private final Pattern p = Pattern.compile(Constants.REGEX_TO_EXTRACT);

    @Override
    void processPage(Document res){
        Matcher m = p.matcher(res.body().text());
        StringBuilder results = new StringBuilder();
        while(m.find()){
            results.append(urlToProcess.toString()).append(",").append(m.group()).append("\n");
        }
        fileUtils.write(results.toString());
        Elements elements = res.body().getElementsByTag("a");
        elements.forEach(element -> {
            String href = element.attr("href");
            try {
                if(urlUtils.isValidUrl(href) && !sharedQueue.alreadyVisited(href))
                    sharedQueue.put(new URL(href),currentUrlLevel+1);
                else if(urlUtils.isValidPath(href)){
                    URL hostWithPath = new URL(urlToProcess.getProtocol(),urlToProcess.getHost(),href);
                    if(!sharedQueue.alreadyVisited(hostWithPath.toString()) && currentUrlLevel+1<=Constants.MAX_LEVEL)
                        sharedQueue.put(hostWithPath,currentUrlLevel+1);
                }
            } catch (MalformedURLException e) {
                log.severe(e.getMessage());
            }
        });
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
                    this.fetchCurrentURL();
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
