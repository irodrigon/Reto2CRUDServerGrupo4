/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.tartanga.grupo4.creditcards.Movement;
import com.tartanga.grupo4.exceptions.CreateException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Iñi
 */
@Stateless
@Path("com.tartanga.grupo4.creditcards.movement")
public class MovementFacadeREST extends AbstractFacade<Movement> {
    
    private static final Logger logger = Logger.getLogger(MovementFacadeREST.class.getName());

    @PersistenceContext(unitName = "Reto2CRUDServerGrupo4PU")
    private EntityManager em;

    public MovementFacadeREST() {
        super(Movement.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Movement entity) {
        try {
            logger.log(Level.INFO, "MovementFacadeREST: Creating movement {0}.", entity);
            super.create(entity);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "MovementFacadeREST: Exception creating movement: {0}", ex.getMessage());
            throw new InternalServerErrorException("Movement creation failed: " + ex.getMessage());
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, Movement entity) {
        try {
            logger.log(Level.INFO, "MovementFacadeREST: Updating movement {0}.", entity);
            super.edit(entity);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "MovementFacadeREST: Exception updating movement: {0}", ex.getMessage());
            throw new InternalServerErrorException("Movement editing failed: " + ex.getMessage());
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        try {
            logger.log(Level.INFO, "MovementFacadeREST: Deleting movement {0}.", id);
            super.remove(super.find(id));
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "MovementFacadeREST: Exception deleting movement: {0}", ex.getMessage());
            throw new InternalServerErrorException("Movement deleting failed: " + ex.getMessage());
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Movement find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Movement> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Movement> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
}
