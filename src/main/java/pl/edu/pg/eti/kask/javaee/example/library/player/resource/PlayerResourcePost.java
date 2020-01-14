package pl.edu.pg.eti.kask.javaee.example.library.player.resource;

import pl.edu.pg.eti.kask.javaee.example.library.player.PlayerService;
import pl.edu.pg.eti.kask.javaee.example.library.player.model.Player;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import static pl.edu.pg.eti.kask.javaee.example.library.resource.UriHelper.uri;

@Path("players")
public class PlayerResourcePost {

    @Context
    private UriInfo info;

    @Inject
    private PlayerService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response savePlayer(Player player) {
        service.savePlayer(player);
        return Response.created(uri(PlayerResourceGet.class, "getPlayer", player.getId())).build();
    }
}
