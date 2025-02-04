/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartanga.grupo4.product;

import com.tartanga.grupo4.accounts.Account;
import com.tartanga.grupo4.creditcards.CreditCard;
import com.tartanga.grupo4.customers.Customer;
import com.tartanga.grupo4.loans.Loan;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author rabio
 */
@Entity
@Table(name="Product", schema="rovobankdb")
@Inheritance(strategy=InheritanceType.JOINED)
@XmlRootElement
public class Product implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer IDProduct;
    
    @Column(name="creation_date")
    @Temporal(TemporalType.DATE)
    protected Date creationDate;
    
    
    @ManyToMany( fetch = FetchType.EAGER)
    @JoinTable(name="customer_product", schema="rovobankdb",joinColumns =  @JoinColumn(name="IDProduct", referencedColumnName="IDProduct"), 
            inverseJoinColumns = @JoinColumn(name="logIn", referencedColumnName="logIn"))
    protected List<Customer> customers;
    
    public Product(){
        this.creationDate = new Date();
    }

    public Integer getIDProduct() {
        return IDProduct;
    }

    public void setIDProduct(Integer IDProduct) {
        this.IDProduct = IDProduct;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

   
    public List<Customer> getCustomers() {
        return customers;
    }
    
    @XmlElement
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (IDProduct != null ? IDProduct.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.IDProduct == null && other.IDProduct != null) || (this.IDProduct != null && !this.IDProduct.equals(other.IDProduct))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "product.product[ id=" + IDProduct + " ]";
    }
    
}
