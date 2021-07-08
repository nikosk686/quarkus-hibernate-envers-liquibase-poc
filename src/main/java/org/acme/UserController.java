package org.acme;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    @Inject
    UserRepository userRepository;

    @GET
    public List<User> getUsers() {
        return userRepository.findAll().list();
    }

    @GET
    @Path("/{id}")
    public User getUser(@PathParam("id") long id) {
        return userRepository.findByIdOptional(id).orElseThrow();
    }

    @Transactional
    @POST
    public User createUser(User user) {
        userRepository.persistAndFlush(user);
        return user;
    }

    @Transactional
    @PUT
    public User updateUsername(User updatedUser) {
        var user =  userRepository.findByIdOptional(updatedUser.getId()).orElseThrow();
        user.setUsername(updatedUser.getUsername());
        return user;
    }

    @Transactional
    @DELETE
    @Path("/{id}")
    public Long deleteUser(@PathParam("id") long id) {
        userRepository.findByIdOptional(id).ifPresent(userRepository::delete);
        return id;
    }

    @GET
    @Path("/history")
    public List<User> getUsersHistory() {
        return userRepository.getHistory();
    }
}