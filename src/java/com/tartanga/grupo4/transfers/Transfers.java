/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartanga.grupo4.transfers;

import com.tartanga.grupo4.accounts.Account;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alin
 */
@Entity
@Table(name="transfer", schema="rovobankDB")
@NamedQueries({
    @NamedQuery(name="findByAll",
            query="Select t from Transfers t")
})
@XmlRootElement
public class Transfers implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer transferId;
    
    private String sender;
    
    private String reciever;
    @Temporal(TemporalType.DATE)
    private Date transferDate;
    
    private Integer Amount;
    
    @Enumerated(EnumType.STRING)
    private Currency currency;
    
    @ManyToOne
    private Account sourceAccount;

    @ManyToOne
    private Account destinationAccount;
    
    public Transfers(){
        this.transferId=0;
        this.sender="";
        this.reciever="";
        this.transferDate=null;
        this.Amount=0;
        this.currency=Currency.EURO;
    }
    public Transfers(Integer transferId,String sender,String reciever,Date transferDate,Integer Amount,Currency currency){
        this.transferId=transferId;
        this.sender=sender;
        this.reciever=reciever;
        this.transferDate=transferDate;
        this.Amount=Amount;
        this.currency = currency;
    }

    public Integer getTransferId() {
        return transferId;
    }

    public void setTransferId(Integer transferId) {
        this.transferId = transferId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public Integer getAmount() {
        return Amount;
    }

    public void setAmount(Integer Amount) {
        this.Amount = Amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(Account sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public Account getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(Account destinationAccount) {
        this.destinationAccount = destinationAccount;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transferId != null ? transferId.hashCode() : 0);
        return hash;
    }
    
     @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transfers)) {
            return false;
        }
        Transfers other = (Transfers) object;
        if ((this.transferId == null && other.transferId != null) || (this.transferId != null && !this.transferId.equals(other.transferId))) {
            return false;
        }
        return true;
    }
}

