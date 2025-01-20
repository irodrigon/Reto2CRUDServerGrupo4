/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.tartanga.grupo4.exceptions.CreateException;
import com.tartanga.grupo4.transfers.Transfers;
import java.sql.Date;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.InternalServerErrorException;

/**
 *
 * @author Iñi
 */
@Stateless
@Path("com.tartanga.grupo4.transfers.transfers")
public class TransfersFacadeREST extends AbstractFacade<Transfers> {
    

    @PersistenceContext(unitName = "Reto2CRUDServerGrupo4PU")
    private EntityManager em;

    private static final Logger LOGGER = Logger.getLogger("javaServer");

    public TransfersFacadeREST() {
        super(Transfers.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Transfers entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Transfers entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Transfers find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Transfers> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Transfers> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("byTransferId/{transferId}")
    @Produces({"application/xml"})
    public Transfers findByID(@PathParam("transferId") Integer transferId) {
        Transfers transfer = null;

        try {
            LOGGER.log(Level.INFO, "TransfersFacadeREST: find id by transferId={0}.", transferId);
            transfer = (Transfers) em.createNamedQuery("findByID", Transfers.class).setParameter("transferId", transferId).getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TransfersFacadeREST: Exception reading id by transferId, {0}", e.getMessage());
            throw new InternalServerErrorException(e);
        }

        return transfer;
    }

    @GET
    @Path("bySender/{sender}")
    @Produces({"application/xml"})
    public List<Transfers> findBySender(@PathParam("sender") String sender) {
        List<Transfers> transfer = null;

        try {
            LOGGER.log(Level.INFO, "TransfersFacadeREST: find transfer by sender={0}.", sender);
            transfer = em.createNamedQuery("findBySender", Transfers.class).setParameter("sender", sender).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TransfersFacadeREST: Exception reading transfer by sender, {0}", e.getMessage());
            throw new InternalServerErrorException(e);
        }

        return transfer;
    }

    @GET
    @Path("byReciever/{reciever}")
    @Produces({"application/xml"})
    public List<Transfers> findByReciever(@PathParam("reciever") String reciever) {
        List<Transfers> transfer = null;

        try {
            LOGGER.log(Level.INFO, "TransfersFacadeREST: find transfers by reciever={0}.", reciever);
            transfer = em.createNamedQuery("findByReciever", Transfers.class).setParameter("reciever", reciever).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TransfersFacadeREST: Exception reading transfers by reciever, {0}", e.getMessage());
            throw new InternalServerErrorException(e);
        }

        return transfer;
    }
    @GET
    @Path("byDates/{startDate}/{endDate}")
    @Produces({"application/xml"})
    public List<Transfers> findByDate(@PathParam("startDate") String startDate, @PathParam("endDate") String endDate){
        List<Transfers> transfer= null;
        
        try{
            
            LOGGER.log(Level.INFO, "TransfersFacadeREST: find tranfers by startDate={0} and endDate={1}.", new Object[]{startDate, endDate});
            transfer = em.createNamedQuery("findByDate",Transfers.class).setParameter("startDate", Date.valueOf(startDate)).setParameter("endDate", Date.valueOf(endDate)).getResultList();
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "TransfersFacadeREST: Exception reading transfers by creation_dates, {0}", e.getMessage());
            throw new InternalServerErrorException(e);
        }
        return transfer;
    }

}
