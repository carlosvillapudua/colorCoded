<% boolean isUserLoggedIn = (boolean) request.getAttribute("isUserLoggedIn"); %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>colorCoded</title>
    <link rel="stylesheet" href="/css/main.css">
      <script src="/js/user-page-loader.js"></script>

  </head>
  <body>
    <nav>
      <ul id="navigation">
        <li><a href="/">Home</a></li>
        <li><a href="/map.html">Map</a></li>
        <li><a href="/feed.jsp">Public Feed</a></li>
      </ul>
    </nav>
    <center>
          <h1>DOOGLER!</h1>
          <center>
           <%
                if (isUserLoggedIn) {
                  String username = (String) request.getAttribute("username");
              %>
                  <a href="/users/<%=username%>">Message</a>
                  <a href="/username.html">Your Profile</a>
                  <a href="/logout">Logout</a>
              <% } else {   %>
                 <a href="/login">Login</a>
              <% } %>
    </center>
    </body>
</html>