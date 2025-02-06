/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.tartanga.grupo4.creditcards.CreditCard;
import com.tartanga.grupo4.exceptions.ReadException;
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
@Path("com.tartanga.grupo4.creditcards.creditcard")
public class CreditCardFacadeREST extends AbstractFacade<CreditCard> {
    
    private static final Logger logger = Logger.getLogger(CreditCardFacadeREST.class.getName());

    @PersistenceContext(unitName = "Reto2CRUDServerGrupo4PU")
    private EntityManager em;

    public CreditCardFacadeREST() {
        super(CreditCard.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(CreditCard entity) {
        try {
            logger.log(Level.INFO, "CreditCardFacadeREST: Creating credit card {0}.", entity);
            super.create(entity);
        }catch(Exception ex){
            logger.log(Level.SEVERE, "CreditCardFacadeREST: Exception creating credit card: {0}", ex.getMessage());
            throw new InternalServerErrorException("Credit card creation failed: " + ex.getMessage());
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, CreditCard entity) {
        try {
            logger.log(Level.INFO, "CreditCardFacadeREST: Updating credit card {0}.", entity);
            super.edit(entity);
        }catch(Exception ex){
            logger.log(Level.SEVERE, "CreditCardFacadeREST: Exception updating credit card: {0}", ex.getMessage());
            throw new InternalServerErrorException("Credit card editing failed: " + ex.getMessage());
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
          try {
            logger.log(Level.INFO, "CreditCardFacadeREST: Deleting credit card {0}.", id);
            super.remove(super.find(id));
          }catch(Exception ex){
            logger.log(Level.SEVERE, "CreditCardFacadeREST: Exception deleting credit card: {0}", ex.getMessage());
            throw new InternalServerErrorException("Credit card deleting failed: " + ex.getMessage());
        }
          
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public CreditCard find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CreditCard> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CreditCard> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    @GET
    @Path("creditCardNumber/{creditCardNumber}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CreditCard> findCreditCardByCreditCardNumber(@PathParam("creditCardNumber") Long creditCardNumber) throws ReadException{
        
        List<CreditCard> creditCardData = new ArrayList<>();
        
        try{
            logger.log(Level.INFO, "CreditCardFacadeREST: Reading credit card data.");
            creditCardData = em.createNamedQuery("findCreditCardByCreditCardNumber").setParameter("creditCardNumber", creditCardNumber).getResultList();
        }catch(Exception e){
            logger.log(Level.SEVERE, "CreditCardFacadeREST: Exception returning credit card data: ", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        
        return creditCardData;
    }
    
    @GET
    @Path("creationDates/{startDate}/{endDate}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CreditCard> findCreditCardByCreationDate(@PathParam("startDate") String startDate, @PathParam("endDate") String endDate) throws ReadException{
        
        List<CreditCard> creditCardData = new ArrayList<>();
        
        try{
            logger.log(Level.INFO, "CreditCardFacadeREST: Reading data by creation date.");
            creditCardData = em.createNamedQuery("findCreditCardByCreationDate").setParameter("startDate",Date.valueOf(startDate)).setParameter("endDate",Date.valueOf(endDate)).getResultList();
        }catch(Exception e){
            logger.log(Level.SEVERE, "CreditCardFacadeREST: Exception reading data by creation date: ", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        
        return creditCardData;
    }
    
    @GET
    @Path("expirationDates/{startDate}/{endDate}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CreditCard> findCreditCardByexpirationDate(@PathParam("startDate") String startDate, @PathParam("endDate") String endDate) throws ReadException{
        
        List<CreditCard> creditCardData = new ArrayList<>();
        
        try{
            logger.log(Level.INFO, "CreditCardFacadeREST: Reading data by expiration date.");
            creditCardData = em.createNamedQuery("findCreditCardByExpirationDate").setParameter("startDate",Date.valueOf(startDate)).setParameter("endDate",Date.valueOf(endDate)).getResultList();
        }catch(Exception e){
            logger.log(Level.SEVERE, "CreditCardFacadeREST: Exception reading data by expiration date: ", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        
        return creditCardData;
    }
    
    @DELETE
    @Path("deleteByCardNumber/{creditCardNumber}")
    public void deleteCreditCardByCardNumber(@PathParam("creditCardNumber") Long creditCardNumber) {
          try {
            logger.log(Level.INFO, "CreditCardFacadeREST: Deleting credit card {0}.", creditCardNumber);
            em.createNamedQuery("deleteCreditCardByCardNumber").setParameter("creditCardNumber", creditCardNumber).executeUpdate();
          }catch(Exception ex){
            logger.log(Level.SEVERE, "CreditCardFacadeREST: Exception deleting credit card: {0}", ex.getMessage());
            throw new InternalServerErrorException("Credit card deleting failed: " + ex.getMessage());
        }
          
    }
}
