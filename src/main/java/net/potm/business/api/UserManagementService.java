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

package net.potm.business.api;

import net.potm.persistence.model.Person;

import javax.ejb.Local;

@Local
public interface UserManagementService {

    Person signUp(String email, String nickName, String firstName, String lastName, String password);
    Person activateUser(String email,String activationCode);
    Person authenticate(String nickOrEmail,String password);
    Boolean isEmailRegistered(String email);
    Boolean isNickRegistered(String nick);
    Person updateUser(Person person);

    void deleteUser(Person person);


}
