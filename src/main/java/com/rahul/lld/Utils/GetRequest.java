package com.rahul.lld.Utils;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.URL;

public class GetRequest {

    public String get(URL url) throws IOException {
        return Jsoup.parse(url,5000).toString();

//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        InputStream responseStream = connection.getInputStream();
//        BufferedInputStream bis = new BufferedInputStream(responseStream);
//        ByteArrayOutputStream buf = new ByteArrayOutputStream();
//        for (int result = bis.read(); result != -1; result = bis.read()) {
//            buf.write((byte) result);
//        }
//        return buf.toString();
    }
}
