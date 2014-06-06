/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.kdomic.sb;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import org.foi.nwtis.kdomic.eb.Logs;
import org.foi.nwtis.kdomic.eb.MeteoPortfolio;
import org.foi.nwtis.kdomic.eb.Users;

/**
 *
 * @author Krunoslav
 */
@Stateless
public class LogsFacade extends AbstractFacade<Logs> {

    @PersistenceContext(unitName = "kdomic_aplikacija_2_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LogsFacade() {
        super(Logs.class);
    }

    public void addNewLog(String action, Users user) {
        Logs log = new Logs(action, new Date(), user);
        this.create(log);
    }

    public void addNewLog(String action) {
        Logs log = new Logs(action, new Date());
        this.create(log);
    }

    public List<Logs> findByDataRange(Date start, Date end) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery();
            Root e = cq.from(Logs.class);
            cq.where(cb.between(e.get("datetime"), start, end));
            Query query = em.createQuery(cq);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
