package net.potm.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AppProperty.class)
public abstract class AppProperty_ {

	public static volatile SingularAttribute<AppProperty, AppPropertyId> id;
	public static volatile SingularAttribute<AppProperty, String> value;

	public static final String ID = "id";
	public static final String VALUE = "value";

}

