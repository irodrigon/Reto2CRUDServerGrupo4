/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartanga.grupo4.customers;


import com.tartanga.grupo4.accounts.Account;
import com.tartanga.grupo4.product.Product;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author rabio
 */
@Entity
@Table(name = "customer", schema = "rovobankDB")
@NamedQueries({
   @NamedQuery(name = "findCustomerByNameSurname", query = "SELECT c FROM Customer c WHERE c.name = :name and c.surname = :surname"),
   @NamedQuery(name = "findCustomerByName",        query = "SELECT c FROM Customer c WHERE c.name = :name"),
   @NamedQuery(name = "findCustomerBySurname",     query = "SELECT c FROM Customer c WHERE c.surname = :surname")
})
@XmlRootElement
public class Customer extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dni;
    private String telephone;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="customer_product", schema="rovobankdb",joinColumns =  @JoinColumn(name="dni", referencedColumnName="dni"), 
            inverseJoinColumns = @JoinColumn(name="IDProduct", referencedColumnName="IDProduct"))
    private List<Product> products;
    

    //Relaciones
    /*@ManyToMany(mappedBy = "customers", fetch = FetchType.EAGER)
    private Set<Account> accounts; 

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }*/

    @XmlTransient
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dni != null ? dni.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.dni == null && other.dni != null) || (this.dni != null && !this.dni.equals(other.dni))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tartanga.grupo4.customers.Customer[ id=" + dni + " ]";
    }

}
