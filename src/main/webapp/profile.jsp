<% String user = (String) request.getSession().getAttribute("user"); %>
<% String aboutMe = (String) request.getAttribute( "aboutMe" ); %>
<% String description = (String) request.getAttribute( "dogDescription" ); %>


<!DOCTYPE html>
<html>
    <head>
        <title>User SetUp</title>
        <link rel="stylesheet" href="css/main.css">
        <script src="/js/navigation-loader.js"></script>
        <link rel="stylesheet" href="/css/user-page.css">
        <script src="https://cdn.ckeditor.com/ckeditor5/11.2.0/classic/ckeditor.js"></script>
        <script>
            function buildUI() {
                document.getElementById( "aboutMe").value = "${aboutMe}";
                document.getElementById( "dogDescription").value = "${dogDescription}";
            }

        </script>
    </head>
    <body data-user="<%= user %>" onload="buildUI()">
        <nav>
            <ul id="navigation">
                <li><a href="/">Home</a></li>
                <li><a href="/map.html">Map</a></li>
                <%
                    if ( (boolean)request.getSession().getAttribute( "isUserLoggedIn" ) ) {
                        String username = (String) request.getSession().getAttribute("user");
                %>
                        <li><a href="/users/<%=username%>">Personal Messages</a></li>
                        <li><a href="/logout">Logout</a></li>
                <%
                    }
                    else {
                %>
                        <li><a href="/login">Login</a></li>
                <%
                    }
                %>
            </ul>
        </nav>
        <div>
            <center><h2>Set up your profile!</h2></center>

            <form id="profile-form" method="POST" action ="/saveprofile">
                Share something about yourself!
                <br/>
                <textarea name="aboutMe" id ="aboutMe" value="<%= aboutMe %>" placeholder="About Me" rows=2></textarea>
                <br/>

                Doogler Name:
                <br/>
                <input type="text" name="dogName" id="dogName" value="${dogName}" placeholder="Name">
                <br/>

                Doogler Breed:
                <br/>
                <input type="text" name="dogBreed" id="dogBreed" value="${dogBreed}" placeholder="Breed">
                <br/>

                Doogler Gender:
                <br/>
                <select name="dogGender" id="dogGender" selected="${dogGender}">
                    <option value ="nothing">Not Selected</option>
                    <option value ="male">Male</option>
                    <option value ="female">Female</option>
                    <option value ="neutered">Neutered</option>
                </select>
                <br/>

                Personality Description:
                <br/>
                <textarea name="dogDescription" id="dogDescription" value="<%= description %>" placeholder="Playful, Hyper" rows="4"></textarea>
                <br/>

                <input type="submit" value="Save">
            </form>
        </div>
    </body>
</html>