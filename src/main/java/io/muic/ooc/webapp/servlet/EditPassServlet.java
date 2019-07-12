package io.muic.ooc.webapp.servlet;

import io.muic.ooc.webapp.Routable;
import io.muic.ooc.webapp.service.SecurityService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class EditPassServlet extends HttpServlet implements Routable {

    private SecurityService securityService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean authorized = securityService.isAuthorized(request);
        if (authorized) {
            response.sendRedirect("/");
        } else {
            response.sendRedirect("/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newPassword = request.getParameter("newPassword");
        String editUsername = request.getParameter("update");
                try {
                    String url = "jdbc:mysql://localhost:3306/jasmine_schema";
                    String db_username = "root";
                    String db_password = "rootpass";
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection(url, db_username, db_password);
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate("UPDATE users SET password='" + newPassword + "' WHERE username='" + editUsername + "'");
                    stmt.close();
                    con.close();
                    securityService.userCredentials.put(editUsername,newPassword);
                    response.sendRedirect("/");
                } catch (Exception e) {
                    System.out.println(e);
                }
    }

    @Override
    public String getMapping() {
        return "/editPass";
    }

    @Override
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}
