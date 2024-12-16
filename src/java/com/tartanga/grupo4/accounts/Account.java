/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartanga.grupo4.accounts;

import com.tartanga.grupo4.customers.Customer;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import com.tartanga.grupo4.product.Product;
import java.util.Set;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 *
 * @author 2dami
 */
@Entity
@Table(name="account", schema="rovobankDB")
@NamedQueries({
    @NamedQuery(name="getAllAccounts",
            query="SELECT u FROM Account u")
})
@XmlRootElement
public class Account extends Product implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accountNumber;
    
    private Double balance;

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
    
    
    //relaciones
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="customer_account",schema="rovobankDB", joinColumns = @JoinColumn(name="IDProduct", referencedColumnName = "IDProduct"),
                inverseJoinColumns = @JoinColumn(name="logIn",referencedColumnName = "logIn"))
    private Set<Customer> customers;

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accountNumber != null ? accountNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.accountNumber == null && other.accountNumber != null) || (this.accountNumber != null && !this.accountNumber.equals(other.accountNumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tartanga.grupo4.accounts.Accounts[ id=" + accountNumber + " ]";
    }
    
}
