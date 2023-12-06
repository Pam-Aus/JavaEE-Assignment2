package cst8218.austin.bouncer.entity;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;

/**
 * Configures JAX-RS for the application.
 * @author Juneau
 */
@ApplicationPath("resources")
public class JAXRSConfiguration extends Application {
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(cst8218.austin.bouncer.resources.JavaEE8Resource.class);
        resources.add(service.BouncerFacadeREST.class);
    }
    
}
