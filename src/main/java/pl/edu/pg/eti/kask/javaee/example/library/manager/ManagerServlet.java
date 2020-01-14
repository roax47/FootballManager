package pl.edu.pg.eti.kask.javaee.example.library.manager;

import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ManagerServlet extends HttpServlet {
    @Inject
    private ManagerService service;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        service.findAllManagers().forEach(manager -> writer.write(String.format("%s\n", manager.getLogin())));
    }
}


