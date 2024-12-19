/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.tartanga.grupo4.accounts.Account;
import com.tartanga.grupo4.customers.Customer;
import com.tartanga.grupo4.exceptions.ReadException;
import java.util.Date;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.InternalServerErrorException;

/**
 *
 * @author Iñi
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
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Account entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Account entity) {
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
    @Override
    public Account findByAccount(@PathParam("accountNumber") Long accountNumber) {
        Account account = null;

        try {
            LOGGER.log(Level.INFO, "AccountFacadeREST: find account by accounNumber={0}.", accountNumber);
            account = super.findByAccount(accountNumber);
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "AccountFacadeREST: Exception reading account by accountNumber, {0}", e.getMessage());
            throw new InternalServerErrorException(e);
        }

        return account;
    }

    @GET
    @Path("byDates/{startDate}/{endDate}")
    @Produces({"application/xml"})
    @Override
    public List<Account> findByDates(@PathParam("startDate") Date startDate, @PathParam("endDate") Date endDate){
        List<Account> accounts= null;
        
        try{
            LOGGER.log(Level.INFO, "AccountFacadeREST: find account by startDate={0} and endDate={1}.", new Object[]{startDate, endDate});
            accounts = super.findByDates(startDate, endDate);
        }catch(ReadException e){
            LOGGER.log(Level.SEVERE, "AccountFacadeREST: Exception reading accounts by creation_dates, {0}", e.getMessage());
            throw new InternalServerErrorException(e);
        }
        return accounts;
    }
    
    @GET
    @Path("namesSurname/{name}/{surname}")
    @Produces({"application/xml"})
    @Override
    public List<Customer> findByNameSurname(@PathParam("name") String name, @PathParam("surname") String surname){
        List<Customer> customers= null;
        
        try{
            if(name.isEmpty()){
                name = null;
            }
            if(surname.isEmpty()){
                surname = null;
            }
            System.out.println(name);
            System.out.println(surname);
            LOGGER.log(Level.INFO, "AccountFacadeREST: find customer by name={0} and/or surname={1}.", new Object[]{name, surname});
            customers = super.findByNameSurname(name, surname);
        }catch(ReadException e){
            LOGGER.log(Level.SEVERE, "AccountFacadeREST: Exception reading customers by name and/or surname, {0}", e.getMessage());
            throw new InternalServerErrorException(e);
        }
        return customers;
    }
}
