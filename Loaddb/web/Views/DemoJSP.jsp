<%-- 
    Document   : DemoJSP
    Created on : 29 Jan 2026, 10:19:48
    Author     : Admin
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <h1>1+1 = <%=1+1 %> </h1>
        <% 
            int x = 10;
            int y = 20;
            int z = x + y;
            out.write("<h2>" + x + " + " + y + " = " + z + "</h2>");
            out.print("<h2>Giá trị z là số chẵn: " + check(z) + "</h2>");
        %>
        
        <%! 
            boolean check(int x){
                return x % 2 == 0;
            }
        %>
    </body>
</html>