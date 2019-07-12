<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.sql.*" %>
<html>
<body>
<h2>Welcome, ${username}</h2>
<p>${error}</p>
</body>
<form method="post">
    <input type="submit" name="logout" value="Logout">
    <br>
    <br>
    <input type="submit" name="add" value="Add New User">
    <br>
    <br>
    <table border="1">
        <tr>
            <td>Username</td>
            <td>Password</td>
        </tr>
        <%
            try
            {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String url="jdbc:mysql://localhost:3306/jasmine_schema";
                String db_username="root";
                String db_password="rootpass";
                String query="select * from users";
                Connection con = DriverManager.getConnection(url,db_username,db_password);
                Statement stmt=con.createStatement();
                ResultSet rs=stmt.executeQuery(query);
                while(rs.next()) {
                    String username = rs.getString("username");
        %>
        <tr>
            <td><%=username%>
                    <%if(!username.equals(request.getSession().getAttribute("username"))){%>
                <button type="submit" name="editUser" value="<%=username%>">Edit</button>
                    <%}%>
            <td><%=rs.getString("password") %>
                    <%if(!username.equals(request.getSession().getAttribute("username"))){%>
                <button type="submit" name="editPass" value="<%=username%>">Edit</button>
                    <%}if(!username.equals(request.getSession().getAttribute("username"))){%>
            <td>
                <button type="submit" name="delete" value="<%=username%>">Delete</button>
            </td>
                <%}%>
        <tr>
                <%
        }
        rs.close();
        stmt.close();
        con.close();

    }catch(Exception e){
        System.out.println(e);
    }
    %>
    </table>
</form>
</html>