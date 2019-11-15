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

import net.potm.persistence.model.Person;
import net.potm.persistence.model.Person_;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

@Stateless
public class PersonService {

    @PersistenceContext
    EntityManager em;

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Person getPersonById(long id) {
        return em.find(Person.class, id);
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Person getPersonByEmail(String email) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Person> criteria = cb.createQuery(Person.class);
        Root<Person> person = criteria.from(Person.class);

        criteria.select(person).where(cb.equal(person.get(Person_.EMAIL), email));

        try {
            return em.createQuery(criteria).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Long getAllPersonCount(Person currentUser) {
        throw new UnsupportedOperationException("Method is not implemented");
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Person> getAllPerson(int first, int pageSize) {
        throw new UnsupportedOperationException("Method is not implemented");
    }

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void createPerson(Person newPerson) {
        newPerson.setCreatedOn(new Date());
        newPerson.setUpdatedOn(new Date());
        em.persist(newPerson);
    }

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public Person updatePerson(Person person) {
        person.setUpdatedOn(new Date());
        return em.merge(person);
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Person reloadPerson(Person person){
        return em.find(Person.class, person.getId());
    }

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void deletePerson(Person person) {
        em.remove(reloadPerson(person));
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Person fetchAddresses(Person person){
        Person attached=em.find(Person.class,person.getId());
        attached.getAddresses().size();
        return attached;
    }
}
