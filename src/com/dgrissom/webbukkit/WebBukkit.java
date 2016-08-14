package com.dgrissom.webbukkit;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.util.List;

public class WebBukkit {
    private static WebBukkit instance = new WebBukkit();

    private WebsiteRequestHandler requestHandler;

    private WebBukkit() {
        this.requestHandler = new BasicWebsiteRequestHandler();
    }

    public WebsiteRequestHandler getRequestHandler() {
        return this.requestHandler;
    }
    public void setRequestHandler(WebsiteRequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }


    public void start(int port) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new RequestHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    private void handle(HttpExchange exchange, File output) throws IOException {
        List<String> lines = FileUtils.readFile(output);
        String response = "";
        for (String line : lines)
            response += line;

        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStreamWriter writer = new OutputStreamWriter(exchange.getResponseBody());
        writer.write(response);
        writer.close();
    }

    private class RequestHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // http://example.org/<REQUEST HERE>
            String request = exchange.getRequestURI().toASCIIString().substring(1); // ASCII string replaces "%20" with " " for example. subtring removes first "/"
            String response = requestHandler.handle(exchange, request);
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStreamWriter writer = new OutputStreamWriter(exchange.getResponseBody());
            writer.write(response);
            writer.close();
        }
    }
}
