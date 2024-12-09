/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartanga.grupo4.accounts;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 2dami
 */
@Entity
@Table(name="accounts", schema="bank")
@NamedQueries({
    @NamedQuery(name="findByAllAccounts",
            query="SELECT * FROM Accounts")
})
@XmlRootElement
public class Accounts implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String accountNumber;
    
    private Integer balance;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }
    
    public String getaccountNumber() {
        return accountNumber;
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
        if (!(object instanceof Accounts)) {
            return false;
        }
        Accounts other = (Accounts) object;
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
