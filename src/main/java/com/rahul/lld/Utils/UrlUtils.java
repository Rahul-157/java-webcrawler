package com.rahul.lld.Utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class UrlUtils {

    public boolean isValidPath(String path){
        return !path.equals("#") && path.startsWith("/");
    }

    public boolean isValidUrl(String url){
        try{
            new URL(url);
            return true;
        } catch (MalformedURLException malformedURLException){
            return false;
        }
    }

    public String encodeURL(String s){
       return URLEncoder.encode(s);
    }

}
