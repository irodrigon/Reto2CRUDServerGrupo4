/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartanga.grupo4.customers;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Iñi
 */
@Entity
@Table(name="Customer", schema="rovobankdb")
@XmlRootElement
public class Customer extends User implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long IDCustomer;
    
    private String dni;
    
    private String phone;
    
    
    public Customer(){}

    public Long getIDCustomer() {
        return IDCustomer;
    }

    public void setIDCustomer(Long IDCustomer) {
        this.IDCustomer = IDCustomer;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (IDCustomer != null ? IDCustomer.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.IDCustomer == null && other.IDCustomer != null) || (this.IDCustomer != null && !this.IDCustomer.equals(other.IDCustomer))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tartanga.grupo4.customers.Customer[ id=" + IDCustomer + " ]";
    }
    
}
