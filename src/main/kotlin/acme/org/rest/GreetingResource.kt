package acme.org.rest

import javax.annotation.security.RolesAllowed
import javax.ws.rs.GET
import javax.annotation.security.PermitAll
import io.quarkus.security.identity.SecurityIdentity
import org.jboss.resteasy.annotations.cache.NoCache
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/greeting")
@PermitAll
class GreetingResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun hello(): String {
        return "Hello openshift"
    }
}