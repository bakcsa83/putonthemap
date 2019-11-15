package net.potm.business.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Address.class)
public abstract class Address_ {

	public static volatile SingularAttribute<Address, String> zip;
	public static volatile SingularAttribute<Address, String> country;
	public static volatile SingularAttribute<Address, String> city;
	public static volatile SingularAttribute<Address, String> address2;
	public static volatile SingularAttribute<Address, Person> person;
	public static volatile SingularAttribute<Address, String> address1;
	public static volatile SingularAttribute<Address, String> companyName;
	public static volatile SingularAttribute<Address, String> name;
	public static volatile SingularAttribute<Address, Long> id;
	public static volatile SingularAttribute<Address, Long> version;
	public static volatile SingularAttribute<Address, Boolean> primary;

	public static final String ZIP = "zip";
	public static final String COUNTRY = "country";
	public static final String CITY = "city";
	public static final String ADDRESS2 = "address2";
	public static final String PERSON = "person";
	public static final String ADDRESS1 = "address1";
	public static final String COMPANY_NAME = "companyName";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String VERSION = "version";
	public static final String PRIMARY = "primary";

}

