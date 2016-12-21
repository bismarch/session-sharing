<%@ page import="org.springframework.http.HttpRequest" %>
<%--
  Created by IntelliJ IDEA.
  User: nimitz
  Date: 2016/11/18
  Time: 22:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>123</title>
    <%
      request.getSession().setAttribute("name","tom");
      System.out.print(session.getAttribute("name"));
    %>
  </head>
  <body>
  </body>
</html>
