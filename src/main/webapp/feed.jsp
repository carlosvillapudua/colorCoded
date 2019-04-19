<%@ page contentType ="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.*" %>
<%@ page import="com.google.codeu.data.Message" %>

<% String user = (String) request.getSession().getAttribute("sessionUser"); %>
<% System.out.println("user : " + user); %>
<% List<Message> messages = (List<Message>) request.getAttribute("messages"); %>


<!DOCTYPE html>
<html>
<head>
<title>Message Feed</title>
   
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/user-page.css">
<!--<link rel="stylesheet" href="css/testStyle.css">-->
    
<script src="https://cdn.ckeditor.com/ckeditor5/11.2.0/classic/ckeditor.js"></script>
<script>
  function showMessageFormIfLoggedIn( parameterUsername ) {
    fetch('/login-status')
          .then((response) => {
            return response.json();
          })
          .then((loginStatus) => {
            if (loginStatus.isLoggedIn &&
                  loginStatus.username == parameterUsername) {
              fetchImageUploadUrlAndShowForm( parameterUsername );
            }
          });
  }

  function fetchImageUploadUrlAndShowForm( parameterUsername ) {
    fetch('/image-upload-url')
        .then((response) => {
          return response.text();
        })



        .then((imageUploadUrl) => {
          const messageForm = document.getElementById('message-form');
          messageForm.action = imageUploadUrl;
          messageForm.classList.remove('hidden');
          document.getElementById('recipientInput').value = parameterUsername;



        });
  }
  // Fetch messages and add them to the page.
  function fetchMessages(){
    const url = '/feed';
    fetch(url).then((response) => {
      return response.json();
    }).then((messages) => {
      const messageContainer = document.getElementById('message-container');
      if(messages.length == 0){
       messageContainer.innerHTML = '<p>There are no posts yet.</p>';
      }
      else{
       messageContainer.innerHTML = '';  
      }
      messages.forEach((message) => {  
       const messageDiv = buildMessageDiv(message);
       messageContainer.appendChild(messageDiv);
      });
    });
  }
  
  function buildMessageDiv(message){
   const usernameDiv = document.createElement('div');
   usernameDiv.classList.add("usernameFormat");
   usernameDiv.classList.add("left-align");
   usernameDiv.appendChild(document.createTextNode(message.user));
   
   const timeDiv = document.createElement('div');
   timeDiv.classList.add('right-align');
   timeDiv.appendChild(document.createTextNode(new Date(message.timestamp)));
   
   const headerDiv = document.createElement('div');
   headerDiv.classList.add('message-header');
   headerDiv.appendChild(usernameDiv);
   headerDiv.appendChild(timeDiv);
   
   const bodyDiv = document.createElement('div');
   bodyDiv.classList.add('message-body');
   bodyDiv.appendChild(document.createTextNode(message.text));
   
   const messageDiv = document.createElement('div');
   messageDiv.classList.add("message-div");
  messageDiv.classList.add("panel");
   messageDiv.classList.add("rounded");
   messageDiv.appendChild(headerDiv);
   messageDiv.appendChild(bodyDiv);

   if(message.imageUrl){
    bodyDiv.innerHTML += '<br/>';
    bodyDiv.innerHTML += '<img src="' + message.imageUrl + '" />';
  }

   return messageDiv;
  }
  
  // Fetch data and populate the UI of the page.
  function buildUI(){

   var x = document.getElementsByTagName("body")[0];
   const parameterUsername = x.getAttribute('data-user');
   alert('parameterUsername' + parameterUsername);
   showMessageFormIfLoggedIn( parameterUsername );
   fetchMessages();
   ClassicEditor.create( document.getElementById('message-input') );

  }
</script>
</head>
<body data-user="${user}" onload="buildUI();">
<nav>
 <ul id="navigation">
  <li><a href="/">Home</a></li>
  <li><a href="/map.html">Map</a></li>
 </ul>
</nav>
 <div id="content">
  <h1>Message Feed</h1>
  <hr/>
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
  <hr/>
  <div id="message-container">Loading...</div>
 </div>
</body>
</html>