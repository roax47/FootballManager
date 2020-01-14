package pl.edu.pg.eti.kask.javaee.example.library.manager.resource;

import pl.edu.pg.eti.kask.javaee.example.library.manager.ManagerService;
import pl.edu.pg.eti.kask.javaee.example.library.manager.model.Manager;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("managers")
public class ManagerResourcePut {

    @Context
    private UriInfo info;

    @Inject
    private ManagerService service;

    @PUT
    @Path("{managerId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateManager(@PathParam("managerId") int playId, Manager manager) {
        Manager original = service.findManager(playId);
        if (original == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else if (original.getId() != manager.getId()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            service.saveManager(manager);
            return Response.ok().build();
        }
    }
}
