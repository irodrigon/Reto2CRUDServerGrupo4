/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.tartanga.grupo4.creditcards.CreditCard;
import com.tartanga.grupo4.customers.Admin;
import com.tartanga.grupo4.exceptions.CreateException;
import com.tartanga.grupo4.exceptions.ReadException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.ArrayList;
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
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import security.Hash;

/**
 *
 * @author Iñi
 */
@Stateless
@Path("com.tartanga.grupo4.customers.admin")
public class AdminFacadeREST extends AbstractFacade<Admin> {
    
    private Hash security = new Hash();
    private static final Logger logger = Logger.getLogger(AdminFacadeREST.class.getName());

    @PersistenceContext(unitName = "Reto2CRUDServerGrupo4PU")
    private EntityManager em;

    public AdminFacadeREST() {
        super(Admin.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Admin entity) {
         try {    
            String hash = security.passwordToHash(entity.getPassword());
            entity.setPassword(hash);
        } catch (NoSuchAlgorithmException error) {
           logger.log(Level.SEVERE, "RovoBankSignUpController: Exception while creating Hash, {0}", error.getMessage());      
           throw new InternalServerErrorException();
        }
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") String id, Admin entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Admin find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Admin> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Admin> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @GET
    @Path("credentials/{logIn}/{password}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Admin findAdminByCredentials(@PathParam("logIn") String logIn, @PathParam("password") String password) throws ReadException{
        
        Admin admin = new Admin();
        
        try{
            logger.log(Level.INFO, "AdminFacadeREST: Searching data by logIn and password.");
            admin = (Admin) em.createNamedQuery("findAdminByCredentials").setParameter("logIn",logIn).setParameter("password",password).getSingleResult();
        }catch(Exception e){
            logger.log(Level.SEVERE, "AdminFacadeREST: Exception reading data by logIn and password: ", e.getMessage());
            throw new NotAuthorizedException(e.getMessage());
        }
        
        return admin;
    }
    
    @GET
    @Path("credentials/{logIn}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Admin countAdminByLogin(@PathParam("logIn") String logIn) throws ReadException{
        
        Admin admin = new Admin();
        
        try{
            logger.log(Level.INFO, "AdminFacadeREST: Searching data by logIn and password.");
            admin = (Admin) em.createNamedQuery("countAdminByLogin").setParameter("logIn",logIn).getSingleResult();
        }catch(Exception e){
            logger.log(Level.SEVERE, "AdminFacadeREST: Exception reading data by logIn and password: ", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        
        return admin;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
