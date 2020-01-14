package pl.edu.pg.eti.kask.javaee.example.library.club.resource;

import pl.edu.pg.eti.kask.javaee.example.library.club.ClubService;
import pl.edu.pg.eti.kask.javaee.example.library.club.model.Club;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("clubs")
public class ClubResourceDelete {

    @Context
    private UriInfo info;

    @Inject
    private ClubService service;

    @DELETE
    @Path("{clubId}")
    public Response deleteClub(@PathParam("clubId") int clooId) {
        Club original = service.findClub(clooId);
        if (original == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            service.removeClub(original);
            return Response.noContent().build();
        }
    }

}
