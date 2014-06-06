/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.kdomic.sb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.foi.nwtis.kdomic.eb.MeteoPortfolio;
import org.foi.nwtis.kdomic.eb.Users;

/**
 *
 * @author Krunoslav
 */
@Stateless
public class MeteoPortfolioFacade extends AbstractFacade<MeteoPortfolio> {

    @PersistenceContext(unitName = "kdomic_aplikacija_2_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MeteoPortfolioFacade() {
        super(MeteoPortfolio.class);
    }

    public List<MeteoPortfolio> findByUser(Users u) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery();
            Root e = cq.from(MeteoPortfolio.class);
            cq.where(cb.equal(e.get("owner"), u));
            Query query = em.createQuery(cq);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
