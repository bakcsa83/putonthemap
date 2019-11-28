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

import net.potm.persistence.model.ContentBase;
import net.potm.persistence.model.ContentBase_;
import net.potm.persistence.model.Person;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.spatial.criterion.SpatialRestrictions;
import org.locationtech.jts.geom.Polygon;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Stateless
public class ContentService implements Serializable {

    @PersistenceContext
    EntityManager em;

    public void saveContent(ContentBase content) {
        content.setCreatedOn(new Date());
        em.persist(content);
    }

    private void buildContentSearchCriteria(final Criteria criteria, Person owner, Date startDate, Date endDate, Polygon polygon) {
        if (owner != null) {
            criteria.add(Restrictions.eq(ContentBase_.OWNER, owner));
        }

        if (startDate != null) {
            criteria.add(Restrictions.ge(ContentBase_.CREATED_ON, startDate));
        }

        if (endDate != null) {
            criteria.add(Restrictions.le(ContentBase_.CREATED_ON, endDate));
        }

        if (polygon != null) {
            criteria.add(SpatialRestrictions.within(ContentBase_.LOCATION, polygon));
        }
    }


    public Long findContentCount(Person owner, Date startDate, Date endDate, Polygon polygon) {
        Session session = em.unwrap(Session.class);
        //noinspection deprecation JPA is not compatible with SpatialRestrictions so a hibernate specific solution is needed
        Criteria criteria = session.createCriteria(ContentBase.class);
        buildContentSearchCriteria(criteria, owner, startDate, endDate, polygon);
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    //JPA is not compatible with SpatialRestrictions so a hibernate specific solution is needed
    public List<ContentBase> findContent(Person owner, Date startDate, Date endDate, Polygon polygon, int firstResult, int maxresults) {
        Session session = em.unwrap(Session.class);
        //noinspection deprecation JPA is not compatible with SpatialRestrictions so a hibernate specific solution is needed
        Criteria criteria = session.createCriteria(ContentBase.class);
        buildContentSearchCriteria(criteria, owner, startDate, endDate, polygon);
        return criteria.setFirstResult(firstResult).setMaxResults(maxresults).list();
    }

    public ContentBase findContentById(long id) {
        return em.find(ContentBase.class, id);
    }

    public void deleteContent(ContentBase content){
        var attached=em.find(ContentBase.class,content.getId());
        em.remove(attached);
    }

    public ContentBase fetchOwner(ContentBase content){
        var attached=em.find(ContentBase.class,content.getId());
        attached.getOwner().getId();
        return attached;
    }

}
