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

package net.potm.test.business;

import net.potm.business.api.UserManagementService;
import net.potm.misc.SecurityUtils;
import net.potm.persistence.service.PersonService;
import net.potm.test.util.TestUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.inject.Inject;

public class UserManagementServiceTest extends Arquillian {

    @Deployment
    public static Archive<?> createTestArchive() {
        return TestUtils.prepareDeployment();
    }

    @Inject
    UserManagementService userManagementService;


    @Test
    public void signUpTest(){
        var email="tvmaci@mtv1.hu";
        var nick="tvmaci1963";
        var firstName="TV";
        var lastName="Maci";
        var password="paprikajancsi";

        var person=userManagementService.signUp(email, nick, firstName, lastName, password);
        Assert.assertNotNull(person);
        Assert.assertEquals(person.getEmail(),email);
        Assert.assertEquals(person.getNickName(),nick);
        Assert.assertEquals(person.getFirstName(),firstName);
        Assert.assertEquals(person.getLastName(),lastName);

        var pwdHash=SecurityUtils.hashPassword(password,person.getPwdSalt());
        Assert.assertEquals(person.getPassword(),pwdHash);

        userManagementService.deleteUser(person);
    }
}
