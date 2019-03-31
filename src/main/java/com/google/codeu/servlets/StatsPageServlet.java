package com.google.codeu.servlets;

import java.io.IOException;
import java.util.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.codeu.data.Datastore;
import com.google.gson.JsonObject;

/**
 * Handles fetching site statistics.
 */
@WebServlet("/stats")
public class StatsPageServlet extends HttpServlet{

    private Datastore datastore;

    @Override
    public void init() {
        datastore = new Datastore();
    }

    /**
     * Responds with site statistics in JSON.
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");

        int messageCount = datastore.getTotalMessageCount();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("messageCount", messageCount);
        response.getOutputStream().println(jsonObject.toString());
    }
    
    public void main() {
    	Scanner scanner = new Scanner(getServletContext().getResourceAsStream("/WEB-INF/UFO_coord.csv"));
    	while(scanner.hasNextLine()) {
    		String line = scanner.nextLine();
    		String[] cells = line.split(",");
    		
    		String state = cells[0];
    		double lat = Double.parseDouble(cells[1]);
    		double lng = Double.parseDouble(cells[2]);
    		
    		System.out.println("state: " + state);
    		System.out.println("lat: " + lat);
    		System.out.println("lng: " + lng);
    		System.out.println();
    	}
    	scanner.close();
    }
}