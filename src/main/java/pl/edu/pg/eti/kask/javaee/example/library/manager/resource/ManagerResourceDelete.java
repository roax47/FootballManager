package pl.edu.pg.eti.kask.javaee.example.library.manager.resource;

import pl.edu.pg.eti.kask.javaee.example.library.manager.ManagerService;
import pl.edu.pg.eti.kask.javaee.example.library.manager.model.Manager;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


@Path("managers")
public class ManagerResourceDelete {

    @Context
    private UriInfo info;

    @Inject
    private ManagerService service;


    @DELETE
    @Path("{managerId}")
    public Response deleteManager(@PathParam("managerId") int playId) {
        Manager original = service.findManager(playId);
        if (original == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            service.removeManager(original);
            return Response.noContent().build();
        }
    }
}
