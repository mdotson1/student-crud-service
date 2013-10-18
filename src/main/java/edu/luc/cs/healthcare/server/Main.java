package edu.luc.cs.healthcare.server;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class Main {
    public static void main(String[] args) throws Exception{
	final String PORT = System.getenv("PORT");
        final int port = (PORT != null) ? Integer.valueOf(PORT) : 8080;
        final Server server = new Server(port);

        final WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        // TODO verify that this is the preferred way
        webapp.setWar("target/student-crud-service.war");
        server.setHandler(webapp);

        server.start();
        server.join();   
    }
}