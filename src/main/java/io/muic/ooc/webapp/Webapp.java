package io.muic.ooc.webapp;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;

import io.muic.ooc.webapp.service.SecurityService;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

public class Webapp {

    public static void main(String[] args) {

        File docBase = new File("src/main/webapp/");
        docBase.mkdirs();
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8082);

        SecurityService securityService = new SecurityService();

        try{
            String url="jdbc:mysql://localhost:3306/jasmine_schema";
            String db_username="root";
            String db_password="rootpass";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url,db_username,db_password);
            Statement stmt=con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM users");

            while(rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                securityService.userCredentials.put(username,password);
                System.out.println(username);
                System.out.println(password);
            }

            rs.close();
            stmt.close();
            con.close();
        }catch(Exception e){
            System.out.println(e);
        }

        ServletRouter servletRouter = new ServletRouter();
        servletRouter.setSecurityService(securityService);

        Context ctx;
        try {
            ctx = tomcat.addWebapp("", docBase.getAbsolutePath());
            servletRouter.init(ctx);

            tomcat.start();
            tomcat.getServer().await();
        } catch (ServletException | LifecycleException ex) {
            ex.printStackTrace();
        }

    }
}
