/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import cst8218.austin.bouncer.entity.Bouncer;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * This is the child class of AbstractFacade class for the Rest API This class
 * adds additional functionalities for bouncer instances (POST, PUT) and checks
 * for input and returns appropriate error messages where necessary
 *
 * @author chiaustin
 */
@Stateless
@Path("cst8218.austin.bouncer.entity.bouncer")
public class BouncerFacadeREST extends AbstractFacade<Bouncer> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    private int yPosition = 1; //default y position for new instances with null y position
    private int xPosition = 1;//default x position for new instances with null x position
    private int yVelocity = 1;//default y velocity for new instances with null y velocity

    public BouncerFacadeREST() {
        super(Bouncer.class);
    }

    /**
     * This method create an entity with id = null and sets all other attributes
     * to the default if they are null. But if the id is not null it returns a
     * bad request as per the requirements
     *
     * @param entity
     * @return Response
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Bouncer entity) {

        if (entity.getId() == null) { // checks if id is null the set all attributes to default if they are null
            setToDefault(entity); //sets attributes to default if they are null

            super.create(entity);
            return Response.status(Response.Status.CREATED).entity(entity).build(); //ok reponse if instance was created successfully
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid request. ID must be null for new entities.").build(); //repond with bad request if id is not null
        }

    }

    /**
     * This method updates a bouncer having POST request id with new non null
     * information given by the Bouncer in the body of the request It check to
     * make sure that a bouncer with that id exist else return valid error
     * message
     *
     * @param id
     * @param updatedBouncer
     * @return Response depending on outcome
     */
    @POST
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBouncer(@PathParam("id") Long id, Bouncer updatedBouncer) {
        // Step 2: Check if the ID exists in the database
        Bouncer existingBouncer = super.find(id);

        if (existingBouncer == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Bouncer with ID " + id + " not found").build(); //return error message if bouncer not found in database
        } else if (id != updatedBouncer.getId()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Bouncer with ID " + id + " does not match that in the body").build(); // return error message if ids dont match
        } else {
            if (updatedBouncer.getXPosition() != null) {
                existingBouncer.setXPosition(updatedBouncer.getXPosition()); //update instance only if new instance x position is not null
            }
            if (updatedBouncer.getYPosition() != null) {
                existingBouncer.setYPosition(updatedBouncer.getYPosition());//update instance only if new instance y position is not null
            }
            if (updatedBouncer.getYVelocity() != null) {
                existingBouncer.setYVelocity(updatedBouncer.getYVelocity());//update instance only if new instance y velocity is not null
            }
            super.edit(existingBouncer); // update bouncer of everything is ok
            return Response.ok(existingBouncer).build();
        }

    }

    /**
     * After a PUT request, bouncer having id passed in URL is completely
     * replaced with the attributes of Bouncer in the body of the request
     *
     * @param id
     * @param entity
     * @return Response based on outcome
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("id") Long id, Bouncer entity) {

        Bouncer existingBouncer = new Bouncer();
        existingBouncer = super.find(id);

        setToDefault(entity); //sets attributes to default if they are null

        if (existingBouncer != null) { //makes sure bouncer exists in database

            if (existingBouncer.getId() != entity.getId()) { //return error message if ids dont match with that of the request body
                return Response.status(Response.Status.NOT_FOUND).entity("Bouncer with ID " + id + " does not match the id value in the body:" + entity.getId()).build();
            } else { //if ids match update attributes with that of the request body

                updateAttributes(existingBouncer, entity);
                super.edit(existingBouncer); // edit instance

                return Response.ok(existingBouncer).build();
            }

        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Bouncer with ID " + id + " not found").build(); //return error if bouncer's id not found in database
        }

    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response editTable(Bouncer entity) {

        return Response.status(Response.Status.BAD_REQUEST).entity("PUT on the root resources (bouncer table) is not allowed").build(); //PUT on the root resource (bouncer table) is not allowed

    }

    /**
     * This method deletes an instances with id from DELETE request
     *
     * @param id
     * @return Response depending on outcome
     */
    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Long id) {
        Bouncer bouncer = super.find(id);

        if (bouncer != null) {
            super.remove(bouncer);
            return Response.noContent().build(); // Return 204 No Content for successful deletion
        } else {
            // If the entity with the specified ID doesn't exist, return a 404 Not Found response
            return Response.status(Response.Status.NOT_FOUND).entity("Bouncer with ID " + id + " not found").build(); //returns error message if that bouncer doesnt exist in database
        }

    }

    /**
     * This method is used to search the database for instance with id passed in
     * GET request and return error message if the instance doesn't exist in
     * database
     *
     * @param id
     * @return Bouncer instance with id passed in GET request
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Bouncer find(@PathParam("id") Long id) {

        Bouncer bouncer = new Bouncer();

        bouncer = super.find(id); //find instnace with that id

        if (bouncer != null) {
            return bouncer; //return Bouncer if found
        } else {
            Response.status(Response.Status.NOT_FOUND).entity("Bouncer with ID " + id + " not found").build(); //return error message if bouncer is not in database
            return null;
        }

    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Bouncer> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Bouncer> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * This private method is used to check if attributes are null and sets the
     * values to default if they are
     *
     * @param entity
     */
    private void setToDefault(Bouncer entity) {
        if (entity.getXPosition() == null) {
            entity.setXPosition(this.xPosition); //default x position for new instances with null x position

        }
        if (entity.getYPosition() == null) {
            entity.setYPosition(this.yPosition);//default y position for new instances with null y position
        }
        if (entity.getYVelocity() == null) {
            entity.setYVelocity(this.yVelocity); //default y velocity for new instances with null y velocity
        }
    }

    /**
     * This method is used to update the bouncer after checking for null
     * attributes from setToDefault method
     */
    private void updateAttributes(Bouncer oldBouncer, Bouncer newBouncer) {
        oldBouncer.setXPosition(newBouncer.getXPosition());
        oldBouncer.setYPosition(newBouncer.getYPosition());
        oldBouncer.setYVelocity(newBouncer.getYVelocity());
    }

}
