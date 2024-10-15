<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <h1>Please Sign In</h1>
    <form action="login" method="post">
        Username: <input type="text" name="username"/><br/>
        Password: <input type="password" name="password"/><br/>
        <input type="submit" value="Sign In"/>
    </form>
    <%
        if ("true".equals(request.getParameter("error"))) {
    %>
        <p style="color:red;">Invalid username or password</p>
    <%
        }
    %>
</body>
</html>
