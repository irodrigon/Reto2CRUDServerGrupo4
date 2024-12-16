/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartanga.grupo4.customers;

import java.io.Serializable;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author Iñi
 */
@MappedSuperclass
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private Long IDUser;
    
    private String username;
    
    private String name;
    
    private String surname;
    
    private String street;
    
    private String city;
    
    private Integer zip;
    
    public User(){}

    public Long getIDUser() {
        return IDUser;
    }

    public void setIDUser(Long IDUser) {
        this.IDUser = IDUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getZip() {
        return zip;
    }

    public void setZip(Integer zip) {
        this.zip = zip;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (IDUser != null ? IDUser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.IDUser == null && other.IDUser != null) || (this.IDUser != null && !this.IDUser.equals(other.IDUser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tartanga.grupo4.customers.User[ id=" + IDUser + " ]";
    }
    
}
