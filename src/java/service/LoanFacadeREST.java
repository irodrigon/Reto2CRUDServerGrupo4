/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;


import com.tartanga.grupo4.customers.Customer;
import com.tartanga.grupo4.exceptions.CreateException;
import com.tartanga.grupo4.loans.Loan;
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
import java.util.logging.Logger;
import javax.ws.rs.InternalServerErrorException;


/**
 *
 * @author Iñi
 */
@Stateless
@Path("com.tartanga.grupo4.loans.loan")
public class LoanFacadeREST extends AbstractFacade<Loan> {
    

    @PersistenceContext(unitName = "Reto2CRUDServerGrupo4PU")
    private EntityManager em;

    private static final Logger LOGGER = Logger.getLogger("javaServer");
    
    public LoanFacadeREST() {
        super(Loan.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Loan entity) {
            super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Loan entity) {
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
    public Loan find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Loan> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Loan> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @GET
    @Path("balance/{balance}")
    @Produces({"application/xml"})
    public List <Loan> findByRemainingBalance(@PathParam("balance") String balance){
      List <Loan> loans = null;
      try{
         LOGGER.log(Level.INFO, "LoanFacadeREST: Getting loans which remaining balance is over {0}", balance);
         loans =  em.createNamedQuery("getBalanceByRemaining", Loan.class).setParameter("balance", Double.parseDouble(balance)).getResultList();
      }catch(Exception e){
          
          LOGGER.log(Level.SEVERE, "LoanFacadeREST: Failed getting loans where remaining balance is over {0}", balance);
          throw new InternalServerErrorException(e);
      }
      
      
      return loans;
    }
    
    
    @GET
    @Path("interestRate/{interestRate}")
    @Produces({"application/xml"})
    public List <Loan> findByInterestRate(@PathParam("interestRate") String interestRate){
            List <Loan> loansInterestRate =null;
            try{
                LOGGER.log(Level.INFO, "LoanFacadeREST: Getting loans which remaining balance is over {0}", interestRate);
                loansInterestRate =  em.createNamedQuery("getLoansByInterestRate", Loan.class).setParameter("interestRate", Integer.parseInt(interestRate)).getResultList();
            }catch(Exception e){
          
          LOGGER.log(Level.SEVERE, "LoanFacadeREST: Failed getting loans where interest rate is over {0}", interestRate);
          throw new InternalServerErrorException(e);
      }
     return loansInterestRate;
    }
    
    
    @GET
    @Path("byDates/{startDate}/{endDate}")
    @Produces({"application/xml"})
    public List<Loan> findByDates(@PathParam("startDate") String startDate, @PathParam("endDate") String endDate){
        List<Loan> loansDates= null;
        
          try{
            
            LOGGER.log(Level.INFO, "LoanFacadeREST: Getting loans which  startDate={0} and endDate={1}.", new Object[]{startDate, endDate});
            loansDates = em.createNamedQuery("findByDatesLoan", Loan.class).setParameter("startDate", Date.valueOf(startDate)).setParameter("endDate", Date.valueOf(endDate)).getResultList();
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "LoanFacadeREST: Exception reading loanss by creation dates between {0} and {0}", new Object[]{startDate, endDate});
            throw new InternalServerErrorException(e);
        }
        return loansDates;
    
    }
        
    
    @GET
    @Path("logIn/{logIn}")
    @Produces({"application/xml"})
    public List <Customer> findByUser(@PathParam("logIn") String logIn){
      List <Customer> loans = null;
      try{
         LOGGER.log(Level.INFO, "LoanFacadeREST: Getting loans which user is {0}", logIn);
         loans =  em.createNamedQuery("getByUser", Customer.class).setParameter("logIn",logIn).getResultList();
      }catch(Exception e){
          
          LOGGER.log(Level.SEVERE, "LoanFacadeREST: Failed getting loans which user is {0}", logIn);
          throw new InternalServerErrorException(e);
      }
      
      
      return loans;
    }
   
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
