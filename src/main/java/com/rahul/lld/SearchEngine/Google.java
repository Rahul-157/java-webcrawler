package com.rahul.lld.SearchEngine;

import com.rahul.lld.Utils.Constants;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Google implements SearchEngine{
    private final String GOOGLE_SEARCH_ENGINE_QUERY = "https://www.google.com/search?q=%s";
    private final Pattern pattern = Pattern.compile("/url\\?q=(.*?)&");
    @Override
    public Set<URL> searchQuery(String searchQuery) throws IOException {
        HashSet<URL> urls = new HashSet<>();
        URL searchURL = new URL(String.format(GOOGLE_SEARCH_ENGINE_QUERY,urlUtils.encodeURL(searchQuery)));
        Document document = getRequest.get(searchURL);
        Elements elements = document.body().getElementsByTag("a");
        elements.forEach(element -> {
            String href = element.attr("href");
            try {
                Matcher matcher = pattern.matcher(href);
                if(matcher.find()){
                    URL resultURL = new URL(matcher.group(1));
                    if(!resultURL.getHost().toLowerCase().contains("google") && !sharedQueue.alreadyVisited(resultURL))
                        urls.add(resultURL);
                }
            } catch (MalformedURLException e) {
                log.severe(e.getMessage());
            }
        });
        return urls;
    }
}
