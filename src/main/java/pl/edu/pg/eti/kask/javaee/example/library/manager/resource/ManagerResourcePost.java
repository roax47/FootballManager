package pl.edu.pg.eti.kask.javaee.example.library.manager.resource;

import pl.edu.pg.eti.kask.javaee.example.library.manager.ManagerService;
import pl.edu.pg.eti.kask.javaee.example.library.manager.model.Manager;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import static pl.edu.pg.eti.kask.javaee.example.library.resource.UriHelper.uri;

@Path("managers")
public class ManagerResourcePost {

    @Context
    private UriInfo info;

    @Inject
    private ManagerService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveManager(Manager manager) {
        service.saveManager(manager);
        return Response.created(uri(ManagerResourceGet.class, "getManager", manager.getId())).build();
    }
}
