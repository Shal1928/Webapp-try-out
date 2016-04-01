package ru.shal1928.tryout.webapp.server.services;

import javax.ws.rs.*;

@Path("/")
public interface RestService {
    @GET
    @Path("echo")
    String echo(@QueryParam("q") String original);
}
