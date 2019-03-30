package com.google.codeu.servlets;

import com.google.codeu.data.Datastore;
import com.google.codeu.data.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users/*")
public class UserServlet extends HttpServlet {

    private Datastore datastore;

    @Override
    public void init() {
        datastore = new Datastore();
    }


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        //UserService userService = UserServiceFactory.getUserService();
        String requestUrl = request.getRequestURI();
        String user = requestUrl.substring("/users/".length());

        // Confirm that user is valid
        if (user == null || user.equals("")) {
            // Request is invalid, return to homepage
            response.getWriter().println("[]");
            return;
        }

        User userData = datastore.getUser(user);

        //if user does not exist send to error page
        if (userData == null) {
            response.sendRedirect("/index.jsp");
            System.out.println("Page not found");
            return;
        }

        /*List<Message> messages = datastore.getMessages(user);
        String aboutMe = userData.getAboutMe();
        boolean isViewingSelf =
                userService.isUserLoggedIn()
                        && userData.getEmail().equals(userService.getCurrentUser().getEmail());
        System.out.println("fetching user messages" + messages);

         Add them to the request
        request.setAttribute("user", user);
        request.setAttribute("messages", messages);
        request.setAttribute("aboutMe", aboutMe);
        request.setAttribute("isUserLoggedIn", userService.isUserLoggedIn());
        request.setAttribute("isViewingSelf", isViewingSelf);*/
        request.getRequestDispatcher("/WEB-INF/user.jsp").forward(request,response);
    }
}