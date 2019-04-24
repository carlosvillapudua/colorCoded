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

@WebServlet("/saveprofile")
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

        String user = (String)request.getSession().getAttribute("user");

        if(user == null || user.equals("")) {
            // Request is invalid, return empty response
            response.sendRedirect("/");
            return;
        }

        User userData = datastore.getUser(user);

        if ( userData != null ) {
            request.setAttribute("username", userData.getEmail());
            request.setAttribute("aboutMe", userData.getAboutMe());
            request.setAttribute("dogName", userData.getDogName());
            request.setAttribute("dogBreed", userData.getBreed());
            request.setAttribute("dogGender", userData.getGender());
            request.setAttribute("dogDescription", userData.getDescription());
        }
        request.getRequestDispatcher( "/profile.jsp").forward( request, response );

        /*String userprofile = userData.getUserName;
        boolean isViewingSelf = userService.isUserLoggedIn() && userData.getEmail().equals(userService.getCurrentUser().getUserName());*/

    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");


        UserService userService = UserServiceFactory.getUserService();
        com.google.appengine.api.users.User currentUser = userService.getCurrentUser();

        String username = request.getParameter("username");
        String aboutMe = request.getParameter("aboutMe");
        String dogName = request.getParameter("dogName");
        String breed = request.getParameter("dogBreed");
        String gender = request.getParameter("dogGender");
        String description = request.getParameter("dogDescription");

        String email = currentUser.getEmail();
        User user = new User(email, aboutMe, dogName, breed, gender, description);
        datastore.storeUser(user);

        request.getRequestDispatcher("index.jsp").forward(request,response);

    }

}






