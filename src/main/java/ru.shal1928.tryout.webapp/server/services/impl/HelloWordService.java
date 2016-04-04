package ru.shal1928.tryout.webapp.server.services.impl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 */
@Path("/hello")
public class HelloWordService {
    @GET
    @Path("/{param}")
    public Response getMsg(@PathParam("param") String msg) {

        String output = "Jersey say : " + msg;

        return Response.status(200).entity(output).build();

    }

    @GET
    @Produces("text/plain")
    public String sayHello(@PathParam("param") String msg) {
        return "Hello World!";
    }
}
