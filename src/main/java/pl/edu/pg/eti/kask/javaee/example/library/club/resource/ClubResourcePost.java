package pl.edu.pg.eti.kask.javaee.example.library.club.resource;

import pl.edu.pg.eti.kask.javaee.example.library.club.ClubService;
import pl.edu.pg.eti.kask.javaee.example.library.club.model.Club;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import static pl.edu.pg.eti.kask.javaee.example.library.resource.UriHelper.uri;


@Path("clubs")
public class ClubResourcePost {

    @Context
    private UriInfo info;

    @Inject
    private ClubService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveClub(Club club) {
        service.saveClub(club);
        return Response.created(uri(ClubResourceGetPaging.class, "getClub", club.getId())).build();
    }
}
