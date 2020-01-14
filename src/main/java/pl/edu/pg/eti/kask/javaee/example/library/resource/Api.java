package pl.edu.pg.eti.kask.javaee.example.library.resource;

import pl.edu.pg.eti.kask.javaee.example.library.club.resource.ClubResourceGetPaging;
import pl.edu.pg.eti.kask.javaee.example.library.manager.resource.ManagerResourceGet;
import pl.edu.pg.eti.kask.javaee.example.library.player.resource.PlayerResourceGet;
import pl.edu.pg.eti.kask.javaee.example.library.resource.model.EmbeddedResource;
import pl.edu.pg.eti.kask.javaee.example.library.resource.model.Link;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/")
public class Api {

    @Context
    private UriInfo info;

    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getApi() {
        EmbeddedResource<Void> embedded = EmbeddedResource.<Void>builder()
                .link("clubs", Link.builder().href(
                        info.getBaseUriBuilder()
                                .path(ClubResourceGetPaging.class)
                                .path(ClubResourceGetPaging.class, "getAllClubs")
                                .build())
                        .build())
                .link("self", Link.builder().href(
                        info.getBaseUriBuilder()
                                .path(Api.class)
                                .path(Api.class, "getApi")
                                .build())
                        .build())
                .link("players", Link.builder().href(
                        info.getBaseUriBuilder()
                                .path(PlayerResourceGet.class)
                                .path(PlayerResourceGet.class, "getAllPlayers")
                                .build())
                        .build())
                .link("managers", Link.builder().href(
                        info.getBaseUriBuilder()
                                .path(ManagerResourceGet.class)
                                .path(ManagerResourceGet.class, "getAllManagers")
                                .build())
                        .build())
                .build();
        return Response.ok(embedded).build();
    }

}
