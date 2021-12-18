package com.rahul.lld.Utils;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

public class FileUtils {
    private static Logger log = Log.LOGGER;

    private static FileUtils fileUtils;

    private FileUtils() {
        if (fileUtils != null)
            throw new RuntimeException("Use FileUtils.getInstance() to obtain FileUtils instance");
    }

    public static FileUtils getInstance() {
        if (fileUtils == null) {
            synchronized (FileUtils.class) {
                if (fileUtils == null)
                    fileUtils = new FileUtils();
            }
        }
        return fileUtils;
    }

    public synchronized void write(String s) {
        try (FileWriter fileWriter = new FileWriter(Constants.RESULTS_CSV)) {
            fileWriter.write(s);
        } catch (IOException e) {
            log.severe(e.getMessage());
        }
    }

    public JSONObject readJSON(String file) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(new FileReader(file));
        JSONObject jsonObject = (JSONObject) obj;
        return jsonObject;
    }
}
