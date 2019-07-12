package io.muic.ooc.webapp;

import io.muic.ooc.webapp.service.SecurityService;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

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

//            stmt.executeUpdate("DROP TABLE IF EXISTS users");
//
//            stmt.executeUpdate("CREATE TABLE users(username TEXT, password TEXT)");
//
//            String jaspass = BCrypt.hashpw("jaspass", BCrypt.gensalt());
//            String soonpass = BCrypt.hashpw("soonpass", BCrypt.gensalt());
//
//            stmt.executeUpdate("INSERT INTO users VALUES('jasmine', '"+ jaspass +"')");
//            stmt.executeUpdate("INSERT INTO users VALUES('soon', '"+ soonpass +"')");

            ResultSet rs = stmt.executeQuery("SELECT * FROM users");

            while(rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                securityService.userCredentials.put(username,password);
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
