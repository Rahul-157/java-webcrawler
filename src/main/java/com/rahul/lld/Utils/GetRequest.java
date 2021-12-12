package com.rahul.lld.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

public class GetRequest {
    private static Logger log= Log.LOGGER;
    public Document get(URL url) throws IOException {
           return Jsoup.connect(url.toString())
            .followRedirects(true)
            .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
            .referrer("http://www.google.com")
            .get();
        
        
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
