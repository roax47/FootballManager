package pl.edu.pg.eti.kask.javaee.example.library.manager.resource;

import pl.edu.pg.eti.kask.javaee.example.library.club.model.Club;
import pl.edu.pg.eti.kask.javaee.example.library.manager.ManagerService;
import pl.edu.pg.eti.kask.javaee.example.library.manager.model.Manager;
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

@Path("managers")
public class ManagerResourceGet {

    @Context
    private UriInfo info;
    @Inject
    private ManagerService service;
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("")// required for url generation
    public Response getAllManagers() {
        List<Manager> managers = service.findAllManagers();
        managers.forEach(manager -> manager.getLinks().put(
                "self",
                Link.builder().href(uri(info, ManagerResourceGet.class,
                "getManager", manager.getId())).build()));
        EmbeddedResource.EmbeddedResourceBuilder<List<Manager>> builder = EmbeddedResource.<List<Manager>>builder()
                .embedded("managers", managers);
        builder.link(
                "api",
                Link.builder().href(uri(info, Api.class,
                        "getApi")).build());
        builder.link(
                "self",
                Link.builder().href(uri(info, ManagerResourceGet.class,
                        "getAllManagers")).build());
        EmbeddedResource<List<Manager>> embedded = builder.build();
        return Response.ok(embedded).build();
    }
    @GET
    @Path("{managerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getManager(@PathParam("managerId") int managerId) {
        Manager manager = service.findManager(managerId);
        if (manager != null) {
            manager.getLinks().put(
                    "self",
                    Link.builder().href(uri(info, ManagerResourceGet.class,
                            "getManager", manager.getId())).build());
            manager.getLinks().put(
                    "club",
                    Link.builder().href(uri(info, ManagerResourceGet.class,
                            "getManagerClub", manager.getId())).build());
            manager.getLinks().put(
                    "delete",
                    Link.builder().href(uri(info, ManagerResourceDelete.class,
                            "deleteManager", manager.getId())).method("delete").build());
            manager.getLinks().put(
                    "managers",
                    Link.builder().href(uri(info, ManagerResourceGet.class,
                            "getAllManagers")).build());
            return Response.ok(manager).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    @GET
    @Path("{managerId}/club")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getManagerClub(@PathParam("managerId") int managerId) {
        Manager manager = service.findManager(managerId);
        if (manager != null) {
            EmbeddedResource<Club> embedded = EmbeddedResource.<Club>builder()
                    .embedded("club", manager.getClub())
                    .link(
                            "manager",
                            Link.builder().href(uri(info, ManagerResourceGet.class,
                                    "getManager", manager.getId())).build())
                    .link(
                            "self",
                            Link.builder().href(uri(info, ManagerResourceGet.class,
                                    "getManagerClub", manager.getId())).build())
                    .build();
            return Response.ok(embedded).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
