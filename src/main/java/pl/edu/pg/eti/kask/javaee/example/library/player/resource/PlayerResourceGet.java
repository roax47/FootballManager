package pl.edu.pg.eti.kask.javaee.example.library.player.resource;

import pl.edu.pg.eti.kask.javaee.example.library.club.model.Club;
import pl.edu.pg.eti.kask.javaee.example.library.player.PlayerService;
import pl.edu.pg.eti.kask.javaee.example.library.player.model.Player;
import pl.edu.pg.eti.kask.javaee.example.library.resource.Api;
import pl.edu.pg.eti.kask.javaee.example.library.resource.model.EmbeddedResource;
import pl.edu.pg.eti.kask.javaee.example.library.resource.model.Link;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

import static pl.edu.pg.eti.kask.javaee.example.library.resource.UriHelper.uri;

@Path("players")
public class PlayerResourceGet {

    @Context
    private UriInfo info;

    @Inject
    private PlayerService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("")// required for url generation
    public Response getAllPlayers() {
        List<Player> players = service.findAllPlayers();

        players.forEach(player -> player.getLinks().put(
                "self",
                Link.builder().href(uri(info, PlayerResourceGet.class,
                        "getPlayer", player.getId())).build()));
        EmbeddedResource.EmbeddedResourceBuilder<List<Player>> builder = EmbeddedResource.<List<Player>>builder()
                .embedded("players", players);
        builder.link(
                "api",
                Link.builder().href(uri(info, Api.class,
                        "getApi")).build());
        builder.link(
                "self",
                Link.builder().href(uri(info, PlayerResourceGet.class,
                        "getAllPlayers")).build());
        EmbeddedResource<List<Player>> embedded = builder.build();
        return Response.ok(embedded).build();
    }
    @GET
    @Path("{playerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlayer(@PathParam("playerId") int playerId) {
        Player player = service.findPlayer(playerId);
        if (player != null) {
            player.getLinks().put(
                    "self",
                    Link.builder().href(uri(info, PlayerResourceGet.class,
                            "getPlayer", player.getId())).build());
            player.getLinks().put(
                    "club",
                    Link.builder().href(uri(info, PlayerResourceGet.class,
                            "getPlayerClub", player.getId())).build());
            player.getLinks().put(
                    "delete",
                    Link.builder().href(uri(info, PlayerResourceDelete.class,
                            "deletePlayer", player.getId())).method("delete").build());
            player.getLinks().put(
                    "players",
                    Link.builder().href(uri(info, PlayerResourceGet.class,
                            "getAllPlayers")).build());
            return Response.ok(player).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    @GET
    @Path("{playerId}/club")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlayerClub(@PathParam("playerId") int playerId) {
        Player player = service.findPlayer(playerId);
        if (player != null) {
            EmbeddedResource<Club> embedded = EmbeddedResource.<Club>builder()
                    .embedded("club", player.getClub())
                    .link(
                            "player",
                            Link.builder().href(uri(info, PlayerResourceGet.class,
                                    "getPlayer", player.getId())).build())
                    .link(
                            "self",
                            Link.builder().href(uri(info, PlayerResourceGet.class,
                                    "getPlayerClub", player.getId())).build())
                    .build();
            return Response.ok(embedded).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
