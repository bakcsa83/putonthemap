/*
 *     (C) 2019 by Zoltan Bakcsa (zoltan@bakcsa.hu)
 *     This file is part of "putonthemap".
 *
 *     putonthemap is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     putonthemap is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with putonthemap.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.potm.persistence.service;


import net.potm.persistence.model.Text;
import net.potm.persistence.model.TextId;
import net.potm.persistence.model.TextId_;
import net.potm.persistence.model.Text_;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class TextService {

    @PersistenceContext
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Text> getAllText() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Text> criteria = cb.createQuery(Text.class);
        Root<Text> text = criteria.from(Text.class);

        criteria.select(text);

        return em.createQuery(criteria).getResultList();
    }

    public Text getTextByKey(String key,String language){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Text> criteria = cb.createQuery(Text.class);
        Root<Text> text = criteria.from(Text.class);

        var textID=new TextId(key,language);
        criteria.select(text).where(cb.equal(text.get(Text_.ID), textID));

        return em.createQuery(criteria).getSingleResult();
    }
}
