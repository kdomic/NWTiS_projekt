/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.kdomic.sb;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.foi.nwtis.kdomic.eb.MeteoPortfolio;
import org.foi.nwtis.kdomic.eb.PortfolioAddresses;

/**
 *
 * @author Krunoslav
 */
@Stateless
public class PortfolioAddressesFacade extends AbstractFacade<PortfolioAddresses> {

    @PersistenceContext(unitName = "kdomic_aplikacija_2_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PortfolioAddressesFacade() {
        super(PortfolioAddresses.class);
    }

    public List<PortfolioAddresses> findByPortfolio(MeteoPortfolio selectedPortfolio) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery();
            Root e = cq.from(PortfolioAddresses.class);
            cq.where(cb.equal(e.get("portfolio"), selectedPortfolio));
            Query query = em.createQuery(cq);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
