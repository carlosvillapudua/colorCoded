<% boolean isUserLoggedIn = (boolean) request.getAttribute("isUserLoggedIn"); %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>colorCoded</title>
    <link rel="stylesheet" href="/css/main.css">
  </head>
  <body>
    <nav>
      <ul id="navigation">
        <li><a href="/">Home</a></li>
        <li><a href="/map.html">Map</a></li>
        <li><a href="/feed.html">Public Feed</a></li>

    <%
      if (isUserLoggedIn) {
        String username = (String) request.getAttribute("username");
    %>
        <a href="/user-page.html?user=<%= username %>">Your Page</a>
        <a href="/logout">Logout</a>
    <% } else {   %>
       <a href="/login">Login</a>
    <% } %>

      </ul>
    </nav>
    <center>
          <h1>Welcome to teamColorCoded!</h1>
          <p>colorCoded is privileged to be the first team in Google CodeU spring 2k19 Yay!!
          Our team is made up of 5 amazing individuals and we bring different things to the team and are super excited to be collaborating for the next 10 weeks.
          We are excited to learn more about each other and grow technically and personally together!</p>
    </center>
    </body>
</html>