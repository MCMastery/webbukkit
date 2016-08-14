package com.dgrissom.webbukkit;

import com.sun.net.httpserver.HttpExchange;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class BasicWebsiteRequestHandler implements WebsiteRequestHandler {
    @Override
    public String handle(HttpExchange exchange, String request) throws IOException {
        File page = FileUtils.getWebsitePage(request);
        List<String> lines = FileUtils.readFile(page);
        String response = "";
        for (String line : lines)
            response += line;
        return response;
    }
}
