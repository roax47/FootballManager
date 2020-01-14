package pl.edu.pg.eti.kask.javaee.example.library.club.resource;

import pl.edu.pg.eti.kask.javaee.example.library.club.ClubService;
import pl.edu.pg.eti.kask.javaee.example.library.club.model.Club;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


@Path("clubs")
public class ClubResourcePut {

    @Context
    private UriInfo info;

    @Inject
    private ClubService service;


    @PUT
    @Path("{clubId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateClub(@PathParam("clubId") int clooId, Club club) {
        Club original = service.findClub(clooId);
        if (original == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else if (original.getId() != club.getId()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            service.saveClub(club);
            return Response.ok().build();
        }
    }
}
