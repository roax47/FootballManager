package pl.edu.pg.eti.kask.javaee.example.library.player.resource;

import pl.edu.pg.eti.kask.javaee.example.library.player.PlayerService;
import pl.edu.pg.eti.kask.javaee.example.library.player.model.Player;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("players")
public class PlayerResourceDelete {

    @Context
    private UriInfo info;

    @Inject
    private PlayerService service;

    @DELETE
    @Path("{playerId}")
    public Response deletePlayer(@PathParam("playerId") int playId) {
        Player original = service.findPlayer(playId);
        if (original == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            service.removePlayer(original);
            return Response.noContent().build();
        }
    }
}
