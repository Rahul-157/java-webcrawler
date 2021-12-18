package com.rahul.lld;


import com.rahul.lld.Utils.Log;
import com.rahul.lld.Utils.SharedQueue;
import com.rahul.lld.WebCrawler.Crawler;

import java.io.IOException;
import java.util.logging.Logger;

public class  Main
{
    private static Logger log= Log.LOGGER;
    private static SharedQueue sharedQueue = SharedQueue.getInstance();
    public static void main( String[] args ) throws IOException {
        if(args.length!=1){
            log.severe("Provide Config file in args");
            return;
        }
        System.setProperty("configFile",args[0]);
        log.info("App Started");
        Crawler crawler = new Crawler();
        crawler.startCrawling();
    
    }
}
