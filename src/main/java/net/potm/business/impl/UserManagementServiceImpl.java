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

package net.potm.business.impl;

import net.potm.business.api.UserManagementService;
import net.potm.persistence.model.Person;
import net.potm.persistence.service.PersonService;
import net.potm.security.SecurityUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;

@Stateless
public class UserManagementServiceImpl implements UserManagementService, Serializable {
    private static final long serialVersionUID = 1669918868770971917L;

    @Inject
    PersonService personService;

    @Override
    public Person signUp(String email, String nickName, String firstName, String lastName, String password) {
        var authCode = SecurityUtils.generateRandomString(50);
        var salt = SecurityUtils.getSalt();
        var pwdHash = SecurityUtils.hashPassword(password, salt);
        var person = new Person(email, nickName, firstName, lastName, pwdHash, salt, authCode, Person.STATUS_NEW, 0);

        personService.createPerson(person);

        return person;
    }

    @Override
    public Person authenticate(String nickOrEmail, String password) {
        return null;
    }

    @Override
    public Boolean isEmailRegistered(String email) {
        var person = personService.getPersonByEmailOrNick(email);
        return person == null ? false : true;
    }

    @Override
    public Person updateUser(Person person) {

        return null;
    }

    @Override
    public void deleteUser(Person person) {

        personService.deletePerson(person);
    }
}
