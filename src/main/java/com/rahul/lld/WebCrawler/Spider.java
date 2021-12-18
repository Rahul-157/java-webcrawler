package com.rahul.lld.WebCrawler;

import com.rahul.lld.Utils.*;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.logging.Logger;

abstract public class Spider {
    protected final static SharedQueue sharedQueue = SharedQueue.getInstance();
    protected final UrlUtils urlUtils = new UrlUtils();
    protected final GetRequest request = new GetRequest();
    protected final static FileUtils fileUtils = FileUtils.getInstance();
    protected static Logger log= Log.LOGGER;
    protected URL urlToProcess;
    protected Integer currentUrlLevel;

    abstract void processPage(Document res);

    public void fetchCurrentURL()  {
         if (this.currentUrlLevel > Constants.MAX_LEVEL)
            return;
        if(sharedQueue.alreadyVisited(urlToProcess))
            return;
        sharedQueue.visitURL(urlToProcess);
        try {
            log.info(String.format("Fetching URL : %s",urlToProcess));
            Document response = request.get(urlToProcess);
            processPage(response);
        } catch (IOException e){
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            log.severe(exceptionAsString);
        }
    }

}
