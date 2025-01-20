/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.tartanga.grupo4.accounts.Account;
import com.tartanga.grupo4.exceptions.CreateException;
import com.tartanga.grupo4.customers.Customer;
import com.tartanga.grupo4.exceptions.ReadException;
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
 * @author Aitor
 */
@Stateless
@Path("com.tartanga.grupo4.accounts.account")
public class AccountFacadeREST extends AbstractFacade<Account> {
    

    @PersistenceContext(unitName = "Reto2CRUDServerGrupo4PU")
    private EntityManager em;

    private static final Logger LOGGER = Logger.getLogger("javaServer");

    public AccountFacadeREST() {
        super(Account.class);
    }
    
    @POST
    @Consumes({"application/xml"})
    public void createAccount(Account account){
        LOGGER.log(Level.INFO, "AccountFacadeREST: Creating a new account");
        try{
            em.persist(account);
            LOGGER.log(Level.INFO, "AccountFacadeREST: Account created");
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "AccountFacadeREST: Exception creating account.{0}",
                    e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
        }
    }
    

   /* @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Account entity) {
        super.create(entity);
    }*/

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Account entity) {
        try{
            LOGGER.log(Level.INFO, "AccountFacadeREST: Updating account");
            super.edit(entity);
        }catch(Exception error){
            LOGGER.log(Level.SEVERE, "AccountFacadeREST: Exception Updating account IDProduct={0}.{1}", new Object[]{id, error.getMessage()});
            throw new InternalServerErrorException(error.getMessage());
        }
        
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        try{
            LOGGER.log(Level.INFO, "AccountFacadeREST: Deleting account");
             super.remove(super.find(id));
        }catch(Exception error){
            LOGGER.log(Level.SEVERE, "AccountFacadeREST: Exception deleting account IDProduct={0}.{1}", new Object[]{id, error.getMessage()});
            throw new InternalServerErrorException(error.getMessage());
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Account find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Account> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Account> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("byAccountNumber/{accountNumber}")
    @Produces({"application/xml"})
    public Account findByAccount(@PathParam("accountNumber") String accountNumber) {
        Account account = null;

        try {
            LOGGER.log(Level.INFO, "AccountFacadeREST: find account by accounNumber={0}.", accountNumber);
            account = (Account) em.createNamedQuery("findByAccountNumber", Account.class).setParameter("accountNumber", accountNumber).getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "AccountFacadeREST: Exception reading account by accountNumber, {0}", e.getMessage());
            throw new InternalServerErrorException(e);
        }

        return account;
    }

    @GET
    @Path("byDates/{startDate}/{endDate}")
    @Produces({"application/xml"})
    public List<Account> findByDates(@PathParam("startDate") String startDate, @PathParam("endDate") String endDate){
        List<Account> accounts= null;
        
        try{
            
            LOGGER.log(Level.INFO, "AccountFacadeREST: find account by startDate={0} and endDate={1}.", new Object[]{startDate, endDate});
            accounts = em.createNamedQuery("findByDates",Account.class).setParameter("startDate", Date.valueOf(startDate)).setParameter("endDate", Date.valueOf(endDate)).getResultList();
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "AccountFacadeREST: Exception reading accounts by creation_dates, {0}", e.getMessage());
            throw new InternalServerErrorException(e);
        }
        return accounts;
    }
    
    @GET
    @Path("byNameSurname/{name}/{surname}")
    @Produces({"application/xml"})
    public List<Customer> findByNameSurname(@PathParam("name") String name, @PathParam("surname") String surname){
        List<Customer> customers= null;
        
        try{
            LOGGER.log(Level.INFO, "AccountFacadeREST: find customer by name={0} and surname={1}.", new Object[]{name, surname});
            customers = em.createNamedQuery("findCustomerByNameSurname", Customer.class).setParameter("name", name).setParameter("surname", surname).getResultList();
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "AccountFacadeREST: Exception reading customers by name and surname, {0}", e.getMessage());
            throw new InternalServerErrorException(e);
        }
        return customers;
    }
    
    @GET
    @Path("byName/{name}")
    @Produces({"application/xml"})
    public List<Customer> findByName(@PathParam("name") String name){
        List<Customer> customers= null;
        
        try{
            LOGGER.log(Level.INFO, "AccountFacadeREST: find customer by name={0}.", name);
            customers = em.createNamedQuery("findCustomerByName", Customer.class).setParameter("name", name).getResultList();
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "AccountFacadeREST: Exception reading customers by name, {0}", e.getMessage());
            throw new InternalServerErrorException(e);
        }
        return customers;
    }
    
    @GET
    @Path("bySurname/{surname}")
    @Produces({"application/xml"})
    public List<Customer> findBySurname(@PathParam("surname") String surname){
        List<Customer> customers= null;
        
        try{
            LOGGER.log(Level.INFO, "AccountFacadeREST: find customer by surname={0}.", surname);
            customers = em.createNamedQuery("findCustomerBySurname", Customer.class).setParameter("surname", surname).getResultList();
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "AccountFacadeREST: Exception reading customers by surname, {0}", e.getMessage());
            throw new InternalServerErrorException(e);
        }
        return customers;
    }
    

}
