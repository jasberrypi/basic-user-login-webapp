package io.muic.ooc.webapp.servlet;


import io.muic.ooc.webapp.Routable;
import io.muic.ooc.webapp.service.SecurityService;

import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Resource @WebServlet
public class HomeServlet extends HttpServlet implements Routable {

    private SecurityService securityService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean authorized = securityService.isAuthorized(request);
        if (authorized) {
            // do MVC in here
            String username = (String) request.getSession().getAttribute("username");
            request.setAttribute("username", username);
            request.getRequestDispatcher("WEB-INF/home.jsp").include(request, response);
        } else {
            response.sendRedirect("/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("logout") != null) {
            securityService.logout(request);
            response.sendRedirect("/login");
        }
        if (request.getParameter("add") != null) {
            request.getRequestDispatcher("WEB-INF/add.jsp").include(request, response);
        }
        if (request.getParameter("delete") != null) {
            String delUsername = request.getParameter("delete");
                request.setAttribute("delUsername", delUsername);
                request.getRequestDispatcher("WEB-INF/delete.jsp").include(request, response);
        }
        if (request.getParameter("editUser") != null) {
            String username = (String) request.getSession().getAttribute("username");
            String editUsername = request.getParameter("editUser");
            request.setAttribute("editUsername", editUsername);
            request.getRequestDispatcher("WEB-INF/editUser.jsp").include(request, response);
        }
        if (request.getParameter("editPass") != null) {
            String username = (String) request.getSession().getAttribute("username");
            String editUsername = request.getParameter("editPass");
            request.setAttribute("editUsername", editUsername);
            request.getRequestDispatcher("WEB-INF/editPass.jsp").include(request, response);
        }
    }

    @Override
    public String getMapping() {
        return "/index.jsp";
    }

    @Override
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}
