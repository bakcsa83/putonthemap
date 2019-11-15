package net.potm.business.service;


import net.potm.business.model.Text;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class TextService {

    @PersistenceContext
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Text> getText() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Text> criteria = cb.createQuery(Text.class);
        Root<Text> text = criteria.from(Text.class);

        criteria.select(text);

        return em.createQuery(criteria).getResultList();
    }
}
