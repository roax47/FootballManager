package pl.edu.pg.eti.kask.javaee.example.library.club.resource;

import pl.edu.pg.eti.kask.javaee.example.library.club.ClubService;
import pl.edu.pg.eti.kask.javaee.example.library.club.model.Club;
import pl.edu.pg.eti.kask.javaee.example.library.resource.Api;
import pl.edu.pg.eti.kask.javaee.example.library.resource.model.EmbeddedResource;
import pl.edu.pg.eti.kask.javaee.example.library.resource.model.Link;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

import static pl.edu.pg.eti.kask.javaee.example.library.resource.UriHelper.pagedUri;
import static pl.edu.pg.eti.kask.javaee.example.library.resource.UriHelper.uri;

@Path("clubs")
public class ClubResourceGetPaging {

    @Context
    private UriInfo info;

    @Inject
    private ClubService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("")// required for url generation
    public Response getAllClubs(@QueryParam("page") @DefaultValue("0") Integer page, @QueryParam("limit") @DefaultValue("4") Integer page_size) {
        if(page < 0 ) page = 0;
        if(page_size<=0) page_size =1;
        List<Club> clubs = service.findAllClubs(page * page_size, page_size);
        clubs.forEach(club -> club.getLinks().put(
                "self",
                Link.builder().href(uri(info, ClubResourceGet.class,
                        "getClub", club.getId())).build()));
        long size = service.countClubs();
        int last = ((int)size-1) / page_size;
        EmbeddedResource.EmbeddedResourceBuilder<List<Club>> builder = EmbeddedResource.<List<Club>>builder()
                .embedded("clubs", clubs);
        builder.link(
                "api",
                Link.builder().href(uri(info, Api.class, "getApi")).build());
        builder.link(
                "self",
                Link.builder().href(pagedUri(info, ClubResourceGetPaging.class,
                        "getAllClubs", page,page_size)).build());
        builder.link(
                "first",
                Link.builder().href(pagedUri(info, ClubResourceGetPaging.class,
                        "getAllClubs", 0,page_size)).build());
        builder.link(
                "last",
                Link.builder().href(pagedUri(info, ClubResourceGetPaging.class,
                        "getAllClubs", last,page_size)).build());
        if (page < last) {
            builder.link(
                    "next",
                    Link.builder().href(pagedUri(info, ClubResourceGetPaging.class,
                            "getAllClubs", page + 1,page_size)).build());
        }
        if (page > 0) {
            builder.link(
                    "previous",
                    Link.builder().href(pagedUri(info, ClubResourceGetPaging.class,
                            "getAllClubs", page - 1,page_size)).build());
        }
        EmbeddedResource<List<Club>> embedded = builder.build();
        return Response.ok(embedded).build();
    }
}
