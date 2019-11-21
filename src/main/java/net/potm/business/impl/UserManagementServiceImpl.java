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
    public Person activateUser(String email, String activationCode) {
        var person = personService.getPersonByEmailOrNick(email);

        if (person == null) {
            throw new UserManagementServiceException("User activation failed. Email address not found.");
        }

        if (person.getStatus() == Person.STATUS_ACTIVE) {
            throw new UserManagementServiceException("User activation failed. Person already active.");
        }

        if (person.getStatus() == Person.STATUS_DISABLED) {
            throw new UserManagementServiceException("User activation failed. Person is disabled.");
        }

        if (!person.getAuthCode().equals(activationCode)) {
            throw new UserManagementServiceException("User activation failed. Auth code does not match.");
        }

        person.setStatus(Person.STATUS_ACTIVE);
        try {
            return personService.updatePerson(person);
        } catch (Exception e) {
            throw new UserManagementServiceException("User activation failed.", e);
        }
    }

    @Override
    public Person authenticate(String nickOrEmail, String password) {
        var person = personService.getPersonByEmailOrNick(nickOrEmail);
        if (person == null) return null;
        var pwdHash = SecurityUtils.hashPassword(password, person.getPwdSalt());
        return pwdHash.equals(person.getPassword()) ? person : null;
    }

    @Override
    public Boolean isEmailRegistered(String email) {
        var person = personService.getPersonByEmail(email);
        return person == null ? false : true;
    }

    @Override
    public Boolean isNickRegistered(String nick) {
        var person = personService.getPersonByNick(nick);
        return person == null ? false : true;
    }

    @Override
    public Person updateUser(Person person) {
        return personService.updatePerson(person);
    }

    @Override
    public void deleteUser(Person person) {

        personService.deletePerson(person);
    }

    public class UserManagementServiceException extends RuntimeException {

        public UserManagementServiceException(String msg) {
            super(msg);
        }

        public UserManagementServiceException(String msg, Exception e) {
            super(msg, e);
        }
    }
}
