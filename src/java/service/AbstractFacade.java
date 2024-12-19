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
import javax.persistence.EntityManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.TemporalType;

/**
 *
 * @author 2dami
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    private static final Logger LOGGER = Logger.getLogger("javaServer");

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    //Metodos @NamedQueries del la entidad Account
    public Account findByAccount(Long accountNumber) throws ReadException {
        Account account = null;
        try {
            LOGGER.info("Account: Finding account by accountNumber.");
            account = (Account) getEntityManager().createNamedQuery("findByAccountNumber", Account.class).setParameter("accountNumber", accountNumber).getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception Finding account by accountNumber:", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return account;
    }
    
    public List<Account> findByDates(Date startDate, Date endDate) throws ReadException{
        List<Account> accounts = null;
        try{
            LOGGER.info("Account: Finding account by dates.");
            accounts = getEntityManager().createNamedQuery("findByDates",Account.class).setParameter("startDate", startDate,TemporalType.DATE).setParameter("endDate", endDate, TemporalType.DATE).getResultList();
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "Exception Finding account by creation_date:", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return accounts;
    }
    
    public List<Customer> findByNameSurname(String name, String surname) throws ReadException{
        List<Customer> customers = null;
        try{
            LOGGER.info("Customer: Finding customers by name and/or surname.");
            customers = getEntityManager().createNamedQuery("findCustomerByNameSurname", Customer.class).setParameter("name", name).setParameter("surname", surname).getResultList();
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "Exception Finding customers by name or/and surname", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return customers;
    }
}
