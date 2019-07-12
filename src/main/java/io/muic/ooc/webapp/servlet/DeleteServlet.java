package io.muic.ooc.webapp.servlet;

import io.muic.ooc.webapp.Routable;
import io.muic.ooc.webapp.service.SecurityService;
import org.apache.commons.lang.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class DeleteServlet extends HttpServlet implements Routable {

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
        if (request.getParameter("yes") != null) {
            String delUsername = request.getParameter("yes");
                try {
                    String url = "jdbc:mysql://localhost:3306/jasmine_schema";
                    String db_username = "root";
                    String db_password = "rootpass";
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection(url, db_username, db_password);
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate("DELETE FROM users WHERE username='" + delUsername + "'");
                    stmt.close();
                    con.close();
                    securityService.userCredentials.remove(delUsername);
                    response.sendRedirect("/");
                } catch (Exception e) {
                    System.out.println(e);
                }
        }
        if (request.getParameter("no") != null) {
            response.sendRedirect("/");
        }
    }

    @Override
    public String getMapping() {
        return "/delete";
    }

    @Override
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}
