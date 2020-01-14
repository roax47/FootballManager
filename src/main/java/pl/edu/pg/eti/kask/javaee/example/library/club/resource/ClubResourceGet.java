package pl.edu.pg.eti.kask.javaee.example.library.club.resource;

import pl.edu.pg.eti.kask.javaee.example.library.club.ClubService;
import pl.edu.pg.eti.kask.javaee.example.library.club.model.Club;
import pl.edu.pg.eti.kask.javaee.example.library.manager.model.Manager;
import pl.edu.pg.eti.kask.javaee.example.library.player.model.Player;
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

@Path("clubs")
public class ClubResourceGet {

    @Context
    private UriInfo info;

    @Inject
    private ClubService service;

    @GET
    @Path("{clubId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClub(@PathParam("clubId") int clubId) {
        Club club = service.findClub(clubId);
        if (club != null) {
            club.getLinks().put(
                    "self",
                    Link.builder().href(uri(info, ClubResourceGet.class,
                            "getClub", club.getId())).build());
            club.getLinks().put(
                    "players",
                    Link.builder().href(uri(info, ClubResourceGet.class,
                            "getClubPlayers", club.getId())).build());
            club.getLinks().put(
                    "manager",
                    Link.builder().href(uri(info, ClubResourceGet.class,
                            "getClubManager", club.getId())).build());
            club.getLinks().put(
                    "delete",
                    Link.builder().href(uri(info, ClubResourceDelete.class,
                            "deleteClub", club.getId()))
                            .method("delete").build());
            club.getLinks().put(
                    "clubs",
                    Link.builder().href(uri(info, ClubResourceGetPaging.class,
                            "getAllClubs")).build());
            return Response.ok(club).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    @GET
    @Path("{clubId}/players")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClubPlayers(@PathParam("clubId") int clubId) {
        Club club = service.findClub(clubId);
        if (club != null) {
            List<Player> players = service.findClubPlayers(club);
            EmbeddedResource<List<Player>> embedded = EmbeddedResource.<List<Player>>builder()
                    .embedded("players", players)
                    .link(
                            "club",
                            Link.builder().href(uri(info, ClubResourceGet.class,
                                    "getClub", club.getId())).build())
                    .link(
                            "self",
                            Link.builder().href(uri(info, ClubResourceGet.class,
                                    "getClubPlayers", club.getId())).build())
                    .build();
            return Response.ok(embedded).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    @GET
    @Path("{clubId}/manager")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClubManager(@PathParam("clubId") int clubId) {
        Club club = service.findClub(clubId);
        if (club != null) {
            EmbeddedResource<Manager> embedded = EmbeddedResource.<Manager>builder()
                    .embedded("manager", club.getManager())
                    .link(
                            "club",
                            Link.builder().href(uri(info, ClubResourceGet.class,
                                    "getClub", club.getId())).build())
                    .link(
                            "self",
                            Link.builder().href(uri(info, ClubResourceGet.class,
                                    "getClubManager", club.getId())).build())
                    .build();
            return Response.ok(embedded).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
