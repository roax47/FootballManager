package pl.edu.pg.eti.kask.javaee.example.library.club;


import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ClubServlet extends HttpServlet {

    @Inject
    private ClubService service;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        service.findAllClubs().forEach(club -> writer.write(String.format("%s\n", club.getName())));
    }
}
