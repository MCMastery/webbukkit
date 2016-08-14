package com.dgrissom.webbukkit;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public interface WebsiteRequestHandler {
    // handles an HTTP request to the website
    // returns a response (e.g. the content to display, HTML for example)
    // http://example.com/<REQUEST HERE>
    String handle(HttpExchange exchange, String request) throws IOException;
}
