/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.kdomic.eb;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Krunoslav
 */
@Entity
@Table(name = "PORTFOLIO_ADDRESSES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PortfolioAddresses.findAll", query = "SELECT p FROM PortfolioAddresses p"),
    @NamedQuery(name = "PortfolioAddresses.findById", query = "SELECT p FROM PortfolioAddresses p WHERE p.id = :id"),
    @NamedQuery(name = "PortfolioAddresses.findByAddress", query = "SELECT p FROM PortfolioAddresses p WHERE p.address = :address")})
public class PortfolioAddresses implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 255)
    @Column(name = "ADDRESS")
    private String address;
    @JoinColumn(name = "PORTFOLIO", referencedColumnName = "ID")
    @ManyToOne
    private MeteoPortfolio portfolio;

    public PortfolioAddresses() {
    }

    public PortfolioAddresses(String address, MeteoPortfolio portfolio) {
        this.address = address;
        this.portfolio = portfolio;
    }

    

    public PortfolioAddresses(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public MeteoPortfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(MeteoPortfolio portfolio) {
        this.portfolio = portfolio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PortfolioAddresses)) {
            return false;
        }
        PortfolioAddresses other = (PortfolioAddresses) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.foi.nwtis.kdomic.database.PortfolioAddresses[ id=" + id + " ]";
    }
    
}
