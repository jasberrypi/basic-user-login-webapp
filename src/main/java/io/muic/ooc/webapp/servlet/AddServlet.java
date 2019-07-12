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

public class AddServlet extends HttpServlet implements Routable {

    private SecurityService securityService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean authorized = securityService.isAuthorized(request);
        if (authorized) {
            request.getRequestDispatcher("WEB-INF/add.jsp").include(request, response);
        } else {
            response.sendRedirect("/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String addUsername = request.getParameter("addUsername"); //13
        String addPassword = request.getParameter("addPassword");
        if (!StringUtils.isBlank(addUsername) && !StringUtils.isBlank(addPassword)) {
            if(!securityService.userCredentials.containsKey(addUsername)) {
                try {
                    String url = "jdbc:mysql://localhost:3306/jasmine_schema";
                    String db_username = "root";
                    String db_password = "rootpass";
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection(url, db_username, db_password);
                    PreparedStatement stmt = con.prepareStatement("INSERT INTO users(username, password) VALUES (?, ?)");
                    stmt.setString(1, addUsername);
                    stmt.setString(2, addPassword);
                    stmt.executeUpdate();
                    stmt.close();
                    con.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
                securityService.userCredentials.put(addUsername, addPassword);
                response.sendRedirect("/"); //14
            } else {
                String error = "Username already exists.";
                request.setAttribute("error", error);
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/add.jsp");
                rd.include(request, response);
            }
        } else {
            String error = "Username or password is missing.";
            request.setAttribute("error", error);
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/add.jsp");
            rd.include(request, response);
        }

    }

    @Override
    public String getMapping() {
        return "/add";
    } //12

    @Override
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}
