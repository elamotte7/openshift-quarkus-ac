package acme.org.rest

import javax.annotation.security.RolesAllowed
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/api/admin")
@RolesAllowed("admin")
//@Authenticated
class AdminResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun admin(): String = "granted"

}