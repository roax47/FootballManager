package pl.edu.pg.eti.kask.javaee.example.library.player.resource;

import pl.edu.pg.eti.kask.javaee.example.library.player.PlayerService;
import pl.edu.pg.eti.kask.javaee.example.library.player.model.Player;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("players")
public class PlayerResourcePut {

    @Context
    private UriInfo info;

    @Inject
    private PlayerService service;

    @PUT
    @Path("{playerId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePlayer(@PathParam("playerId") int playId, Player player) {
        Player original = service.findPlayer(playId);
        if (original == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else if (original.getId() != player.getId()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            service.savePlayer(player);
            return Response.ok().build();
        }
    }
}
