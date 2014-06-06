/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.kdomic.eb;

import java.io.Serializable;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Krunoslav
 */
@Entity
@Table(name = "METEO_PORTFOLIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MeteoPortfolio.findAll", query = "SELECT m FROM MeteoPortfolio m"),
    @NamedQuery(name = "MeteoPortfolio.findById", query = "SELECT m FROM MeteoPortfolio m WHERE m.id = :id"),
    @NamedQuery(name = "MeteoPortfolio.findByName", query = "SELECT m FROM MeteoPortfolio m WHERE m.name = :name")})
public class MeteoPortfolio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 255)
    @Column(name = "NAME")
    private String name;
    @OneToMany(mappedBy = "portfolio")
    private List<PortfolioAddresses> portfolioAddressesList;
    @JoinColumn(name = "OWNER", referencedColumnName = "ID")
    @ManyToOne
    private Users owner;

    public MeteoPortfolio() {
    }

    public MeteoPortfolio(Integer id) {
        this.id = id;
    }
    
    public MeteoPortfolio(String name, Users owner) {
        this.name = name;
        this.owner = owner;
    } 
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public List<PortfolioAddresses> getPortfolioAddressesList() {
        return portfolioAddressesList;
    }

    public void setPortfolioAddressesList(List<PortfolioAddresses> portfolioAddressesList) {
        this.portfolioAddressesList = portfolioAddressesList;
    }

    public Users getOwner() {
        return owner;
    }

    public void setOwner(Users owner) {
        this.owner = owner;
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
        if (!(object instanceof MeteoPortfolio)) {
            return false;
        }
        MeteoPortfolio other = (MeteoPortfolio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.foi.nwtis.kdomic.database.MeteoPortfolio[ id=" + id + " ]";
    }
    
}
