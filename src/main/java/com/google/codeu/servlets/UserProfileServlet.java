package com.google.codeu.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.codeu.data.Datastore;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.codeu.data.User;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.ServletException;

@WebServlet("/username")
public class UserProfileServlet extends HttpServlet {

    private Datastore datastore;

    @Override
    public void init() {
        datastore = new Datastore();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        response.setContentType("text/html");

        String user = request.getParameter("user");

        if(user == null || user.equals("")) {
            // Request is invalid, return empty response
            response.sendRedirect("/");
            return;
        }

        User userData = datastore.getUser(user);

        if(userData == null) {
            response.getOutputStream().println("Set Up Your Profile");
            response.sendRedirect("/username.html?users");
            return;
        }

        /*String userprofile = userData.getUserName;
        boolean isViewingSelf = userService.isUserLoggedIn() && userData.getEmail().equals(userService.getCurrentUser().getUserName());*/

    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");


        UserService userService = UserServiceFactory.getUserService();
        String profile = userService.getCurrentUser().getUserId();

        if(!userService.isUserLoggedIn()) {
            response.sendRedirect("/");
            return;
        }


        String userEmail = userService.getCurrentUser().getEmail();
        String aboutMe = request.getParameter("about-me");
        String username = request.getParameter("username");
        String numOfDogs = request.getParameter("numOfDogs");
        String dogName = request.getParameter("dogName");
        String breed = request.getParameter("breed");
        String gender = request.getParameter("gender");
        String description = request.getParameter("description");





        User user= datastore.getUser(userEmail);
        user = new User(userEmail, aboutMe, username, numOfDogs, dogName, breed, gender, description);
        datastore.storeUser(user);

        request.getRequestDispatcher("/username.html?users").forward(request,response);

    }

}






