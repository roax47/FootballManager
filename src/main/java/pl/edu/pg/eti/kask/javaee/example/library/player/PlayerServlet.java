package pl.edu.pg.eti.kask.javaee.example.library.player;

import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class PlayerServlet extends HttpServlet {

    @Inject
    private PlayerService service;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        service.findAllPlayers().forEach(player -> writer.write(String.format("%s\n", player.getLastName())));
    }
}
