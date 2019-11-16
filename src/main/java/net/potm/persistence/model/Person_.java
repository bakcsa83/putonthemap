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

package net.potm.persistence.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Person.class)
public abstract class Person_ {

	public static volatile SingularAttribute<Person, String> pwdSalt;
	public static volatile SingularAttribute<Person, Date> updated_on;
	public static volatile SingularAttribute<Person, String> lastName;
	public static volatile SetAttribute<Person, Address> addresses;
	public static volatile SingularAttribute<Person, String> authCode;
	public static volatile SetAttribute<Person, Role> roles;
	public static volatile SingularAttribute<Person, Long> activationEmailCount;
	public static volatile SingularAttribute<Person, Long> version;
	public static volatile SingularAttribute<Person, String> firstName;
	public static volatile SingularAttribute<Person, String> password;
	public static volatile SingularAttribute<Person, Date> created_on;
	public static volatile SingularAttribute<Person, Long> id;
	public static volatile SingularAttribute<Person, String> email;
	public static volatile SingularAttribute<Person, String> nickName;
	public static volatile SingularAttribute<Person, Long> status;

	public static final String PWD_SALT = "pwdSalt";
	public static final String UPDATED_ON = "updated_on";
	public static final String LAST_NAME = "lastName";
	public static final String ADDRESSES = "addresses";
	public static final String AUTH_CODE = "authCode";
	public static final String ROLES = "roles";
	public static final String ACTIVATION_EMAIL_COUNT = "activationEmailCount";
	public static final String VERSION = "version";
	public static final String FIRST_NAME = "firstName";
	public static final String NICK_NAME = "nickName";
	public static final String PASSWORD = "password";
	public static final String CREATED_ON = "created_on";
	public static final String ID = "id";
	public static final String EMAIL = "email";
	public static final String STATUS = "status";

}

