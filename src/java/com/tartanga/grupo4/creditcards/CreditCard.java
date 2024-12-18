/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartanga.grupo4.creditcards;

import com.tartanga.grupo4.accounts.Account;
import com.tartanga.grupo4.product.Product;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Iñi
 */
@Entity
@Table(name="Credit_card",schema="rovobankdb")
@XmlRootElement
public class CreditCard extends Product implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long creditCardNumber;
    
    @Temporal(TemporalType.DATE) 
    @Column(name="creation_date", insertable=true, updatable=true) 
    protected Date creationDate;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date expirationDate;
    
    private String cvv;
    
    private String pin;
    
    @ManyToOne
    private Account account;
    
    @OneToMany(cascade=ALL, mappedBy="creditCard")
    private List<Movement> movementList;
    
    public CreditCard(){
        this.creationDate = super.creationDate;
    }

    public Long getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(Long creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @XmlTransient
    public List<Movement> getMovementList() {
        return movementList;
    }

    public void setMovementList(List<Movement> movementList) {
        this.movementList = movementList;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (creditCardNumber != null ? creditCardNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CreditCard)) {
            return false;
        }
        CreditCard other = (CreditCard) object;
        if ((this.creditCardNumber == null && other.creditCardNumber != null) || (this.creditCardNumber != null && !this.creditCardNumber.equals(other.creditCardNumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tartanga.grupo4.creditcards.CreditCard[ id=" + creditCardNumber + " ]";
    }
    
}
