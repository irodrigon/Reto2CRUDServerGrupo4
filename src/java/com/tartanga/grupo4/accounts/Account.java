
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartanga.grupo4.accounts;

import com.tartanga.grupo4.creditcards.CreditCard;
import com.tartanga.grupo4.customers.Customer;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import com.tartanga.grupo4.product.Product;
import com.tartanga.grupo4.transfers.Transfers;
import java.util.Date;
import java.util.List;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 2dami
 */
@Entity
@Table(name="account", schema ="rovobankdb")
@NamedQueries({
    @NamedQuery(name="getAllAccounts",query="SELECT u FROM Account u"),
    @NamedQuery(name="findByAccountNumber", query="SELECT a FROM Account a WHERE a.accountNumber = :accountNumber"),
    @NamedQuery(name="findByDates", query="SELECT a FROM Account a WHERE a.creationDate BETWEEN :startDate AND :endDate")
            
})
@XmlRootElement
public class Account extends Product implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Column(unique = true)
    private String accountNumber;
    
    private Double balance;
    
    @Temporal(TemporalType.DATE) 
    @Column(name="creation_date", insertable=true, updatable=true) 
    protected Date creationDate;
    

    @OneToMany(cascade=ALL, mappedBy="account")
    private List<CreditCard> creditCardList;
    
    @OneToMany(cascade=ALL, mappedBy = "sourceAccount")  
    private List<Transfers> outgoingTransfers;
    
    @OneToMany(cascade=ALL, mappedBy = "destinationAccount")  
    private List<Transfers> incomingTransfers;

    public Account() {
        this.creationDate = super.creationDate;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @XmlTransient
    public List<Transfers> getOutgoingTransfers() {
        return outgoingTransfers;
    }

    public void setOutgoingTransfers(List<Transfers> outgoingTransfers) {
        this.outgoingTransfers = outgoingTransfers;
    }

    @XmlTransient
    public List<Transfers> getIncomingTransfers() {
        return incomingTransfers;
    }

    public void setIncomingTransfers(List<Transfers> incomingTransfers) {
        this.incomingTransfers = incomingTransfers;
    }
    
    //relaciones
    /*@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="customer_account",schema="rovobankDB", joinColumns = @JoinColumn(name="IDProduct", referencedColumnName = "IDProduct"),
                inverseJoinColumns = @JoinColumn(name="logIn",referencedColumnName = "logIn"))
    private Set<Customer> customers;

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }*/

    @XmlTransient
    public List<CreditCard> getCreditCardList() {
        return creditCardList;
    }

    public void setCreditCardList(List<CreditCard> creditCardList) {
        this.creditCardList = creditCardList;
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
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.IDProduct == null && other.IDProduct != null) || (this.IDProduct != null && !this.IDProduct.equals(other.IDProduct))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tartanga.grupo4.accounts.Accounts[ id=" + IDProduct + " ]";
    }
    
}
