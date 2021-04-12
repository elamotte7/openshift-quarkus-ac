package acme.org.rest

import javax.annotation.security.RolesAllowed
import javax.ws.rs.GET
import javax.annotation.security.PermitAll
import io.quarkus.security.identity.SecurityIdentity
import org.jboss.resteasy.annotations.cache.NoCache
import javax.inject.Inject
import javax.ws.rs.Path

@Path("/api/users")
class UsersResource {
    @Inject
    var identity: SecurityIdentity? = null
    @GET
    @Path("/me")
    @NoCache
    @RolesAllowed("user")
    fun me(): User {
        return User(identity)
    }

    class User internal constructor(identity: SecurityIdentity?) {
        val userName: String

        init {
            userName = identity!!.principal.name
        }
    }
}