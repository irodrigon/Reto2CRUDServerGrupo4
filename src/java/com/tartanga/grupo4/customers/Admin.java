/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartanga.grupo4.customers;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Iñi
 */
@Entity
@Table(name="Admin", schema="rovobankdb")
@NamedQueries({
    @NamedQuery(
            name="findAdminByCredentials",
            query="SELECT a from Admin a WHERE logIn = :logIn AND password = :password"
    ),
    @NamedQuery(
            name="countAdminByLogin",
            query="SELECT a FROM Admin a WHERE (SELECT COUNT(a) FROM Admin a WHERE logIn = :logIn) > 0"
    )
})
@XmlRootElement
public class Admin extends User implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long IDAdmin;
    
    private String password;
    
    private Boolean active;
    
    public Admin(){}

    public Long getIDAdmin() {
        return IDAdmin;
    }

    public void setIDAdmin(Long IDAdmin) {
        this.IDAdmin = IDAdmin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (IDAdmin != null ? IDAdmin.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Admin)) {
            return false;
        }
        Admin other = (Admin) object;
        if ((this.IDAdmin == null && other.IDAdmin != null) || (this.IDAdmin != null && !this.IDAdmin.equals(other.IDAdmin))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tartanga.grupo4.customers.Admin[ id=" + IDAdmin + " ]";
    }
    
}
