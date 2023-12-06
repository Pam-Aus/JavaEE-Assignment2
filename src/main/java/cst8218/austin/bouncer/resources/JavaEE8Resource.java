package cst8218.austin.bouncer.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *This is the resource class for this Java EE 8 web application for building Restful 
 * web services
 * @author chiaustin
 */
@Path("javaee8")
public class JavaEE8Resource {
    
    @GET
    public Response ping(){
        return Response
                .ok("ping")
                .build();
    }
}
