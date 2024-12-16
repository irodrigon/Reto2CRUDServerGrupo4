/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartange.grupo4.product;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 2dami
 */

//@Table(name="product", schema="rovobankDB")
//@Inheritance(strategy = InheritanceType.JOINED)
@MappedSuperclass
@XmlRootElement
public class Product implements Serializable {
    
    public Product (){
        
    }
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer IDProduct;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date creationDate;
    
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
        return "com.tartange.grupo4.product.Product[ id=" + IDProduct + " ]";
    }
    
}
