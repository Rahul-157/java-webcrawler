package com.rahul.lld.SearchEngine;

import com.rahul.lld.Utils.GetRequest;
import com.rahul.lld.Utils.Log;
import com.rahul.lld.Utils.SharedQueue;
import com.rahul.lld.Utils.UrlUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public interface SearchEngine {
    static final SharedQueue sharedQueue = SharedQueue.getInstance();
    static final UrlUtils urlUtils = new UrlUtils();
    static final GetRequest getRequest = new GetRequest();
    static final Logger log = Log.LOGGER;
    Set<URL> searchQuery(String searchQuery) throws IOException;
}
