/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartanga.grupo4.creditcards;

import com.tartanga.grupo4.enums.Currency;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Iñi
 */
@Entity
@Table(name="Movements",schema="rovobankdb")
@XmlRootElement
public class Movement implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long IDMovement;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date transactionDate;
    
    private Double amount;
    
    @Enumerated(EnumType.STRING)
    private Currency currency;
    
    @ManyToOne
    private CreditCard creditCard;
    
    public Movement(){}

    public Long getIDMovement() {
        return IDMovement;
    }

    public void setIDMovement(Long IDMovement) {
        this.IDMovement = IDMovement;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
    
    @XmlTransient
    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (IDMovement != null ? IDMovement.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Movement)) {
            return false;
        }
        Movement other = (Movement) object;
        if ((this.IDMovement == null && other.IDMovement != null) || (this.IDMovement != null && !this.IDMovement.equals(other.IDMovement))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tartanga.grupo4.creditcards.Movements[ id=" + IDMovement + " ]";
    }
    
}
