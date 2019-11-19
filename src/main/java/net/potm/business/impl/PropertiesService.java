package net.potm.business.impl;

import net.potm.persistence.model.AppProperty;
import net.potm.persistence.model.AppPropertyId;
import net.potm.persistence.model.AppProperty_;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class PropertiesService {
	private final static String SEPARATOR = "#";

	// Profiles
	public static final String TEST_PROFILE = "TEST";
	public static final String DEV_PROFILE = "DEV";
	public static final String PRODUCTION_PROFILE = "PRODUCTION";
	public static final String GLOBAL_PROFILE = "GLOBAL";

	// Property keys
	public static final String ACTIVE_PROFILE = "ACTIVE_PROFILE";

	public static final String DELETED_USER_ID = "DELETED_USER_ID";
	public static final String SYSTEM_USER_ID = "SYSTEM_USER_ID";

	private String activeProfile;
	private Map<String, String> properties = new ConcurrentHashMap<String, String>();

	@Inject
	Logger log;

	@PersistenceContext
	EntityManager em;

	@PostConstruct
	public void init() {
		log.info("PropertiesService initialized.");
		reloadProperties();
	}

	@Lock(javax.ejb.LockType.WRITE)
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void reloadProperties() {
		properties.clear();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<AppProperty> criteria = cb.createQuery(AppProperty.class);
		Root<AppProperty> property = criteria.from(AppProperty.class);
		criteria.select(property);

		em.createQuery(criteria).getResultList().forEach(e -> {
			properties.put(String.format("%s%s%s", e.getId().getKey(), SEPARATOR, e.getId().getProfile()),
					e.getValue());
			if(e.getId().getKey().equals(PropertiesService.ACTIVE_PROFILE)) {
				activeProfile=e.getValue();
				log.info("Active profile is: "+activeProfile);
			}
		});
	}

	public String getProperty(String key, String profile) {
		return properties.get(String.format("%s%s%s", key.toUpperCase(), SEPARATOR, profile.toUpperCase()));
	}

	public String getProperty(String key) {
		return getProperty(key, activeProfile);
	}

	@Lock(javax.ejb.LockType.READ)
	public String getActiveProfile() {
		return activeProfile;
	}

	@Lock(javax.ejb.LockType.WRITE)
	public void setActiveProfile(String activeProfile) {
		this.activeProfile = activeProfile;
		setProperty(PropertiesService.ACTIVE_PROFILE, PropertiesService.GLOBAL_PROFILE, activeProfile);
		log.log(Level.INFO, "Active profile is now: " + this.activeProfile);
	}
	
	@Lock(javax.ejb.LockType.WRITE)
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void setProperty(String key,String profile,String value) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<AppProperty> criteria = cb.createQuery(AppProperty.class);
		Root<AppProperty> property = criteria.from(AppProperty.class);

		var id=new AppPropertyId();
		id.setKey(key);
		id.setProfile(profile);
		
		criteria.select(property)
				.where(cb.equal(property.get(AppProperty_.ID), id));

		try {
			var prop = em.createQuery(criteria).getSingleResult();
			prop.setValue(value);
			em.merge(prop);
		} catch (NoResultException e) {
			var newProperty=new AppProperty();
			newProperty.setId(id);
			newProperty.setValue(value);
			em.persist(newProperty);
		}
	}
}
