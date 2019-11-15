package net.potm.business.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Role.class)
public abstract class Role_ {

	public static volatile SetAttribute<Role, Person> persons;
	public static volatile SetAttribute<Role, Privilege> privileges;
	public static volatile SingularAttribute<Role, Date> created_on;
	public static volatile SingularAttribute<Role, String> name;
	public static volatile SingularAttribute<Role, Long> id;
	public static volatile SingularAttribute<Role, Long> version;

	public static final String PERSONS = "persons";
	public static final String PRIVILEGES = "privileges";
	public static final String CREATED_ON = "created_on";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String VERSION = "version";

}

