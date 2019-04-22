<%@ page contentType ="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.*" %>
<%@ page import="com.google.codeu.data.Message" %>

<% boolean isUserLoggedIn = (boolean) request.getAttribute("isUserLoggedIn"); %>
<% List<Message> messages = (List<Message>) request.getAttribute("messages"); %>
<% String user = (String) request.getAttribute("user"); %>
<% boolean isViewingSelf = (boolean) request.getAttribute("isViewingSelf"); %>


<!DOCTYPE html>
<html>
  <head>
    <title>Chat</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/css/main.css">
    <link rel="stylesheet" href="/css/user-page.css">
    <script src="/js/user-page-loader.js"></script>
    <script src="https://cdn.ckeditor.com/ckeditor5/11.2.0/classic/ckeditor.js"></script>
  </head>
  <body data-isUserLoggedIn="${isUserLoggedIn}" data-user="${user}" data-messages="${messages}" onload="buildUI();">
    <nav>
      <ul id="navigation">
        <li><a href="/">Home</a></li>
        <li><a href="/map.html">Map</a></li>
        <li><a href="/feed.jsp">Public Feed</a></li>
      </ul>
    </nav>
    <h1 id="page-title"></h1>

    <form id="message-form" method="POST" class="hidden" enctype="multipart/form-data">
      Enter a new message:
      <br/>
      <textarea name="text" id="message-input"></textarea>
      <br/>
      Add an image to your message:
      <input type="file" name="image">
      <br/>
      <input type="hidden" value="" name="recipient" id="recipientInput">
      <input type="submit" value="Submit">
    </form>

  </body>
</html>