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

package net.potm.test.service;

import net.potm.persistence.model.Person;
import net.potm.persistence.service.PersonService;
import net.potm.test.util.TestUtils;
import net.potm.util.security.SecurityUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class PersonServiceTest extends Arquillian {

    @Deployment
    public static Archive<?> createTestArchive() {
        return TestUtils.prepareDeployment();
    }

    @Inject
    PersonService personService;

    @Test
    public void persistenceTest(){
        var firstName=SecurityUtils.generateRandomString(8);
        var lastName=SecurityUtils.generateRandomString(8);
        var nick=SecurityUtils.generateRandomString(8);
        var email=TestUtils.getRandomEmail();
        var password=SecurityUtils.generateRandomString(8);
        var salt= SecurityUtils.getSalt();
        var pwdHash=SecurityUtils.hashPassword(password, salt);
        var authCode = SecurityUtils.generateRandomString(50);

        var person = new Person(email, nick, firstName, lastName, pwdHash, salt, authCode, Person.STATUS_NEW, 0);
        person.setPassword(SecurityUtils.hashPassword(password, salt));
        person.setPwdSalt(salt);

        personService.createPerson(person);
        Assert.assertNotNull(person.getId());

        var person2=personService.getPersonByEmailOrNick(nick);
        Assert.assertNotNull(person2);

        person2=personService.getPersonByEmailOrNick(email);
        Assert.assertNotNull(person2);

        person2=personService.getPersonById(person2.getId());
        Assert.assertNotNull(person2);

        personService.deletePerson(person2);
    }
}
