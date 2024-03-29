package net.potm.persistence.model;
// Generated Sep 30, 2019 4:26:28 PM by Hibernate Tools 5.2.11.Final

import javax.persistence.*;

/**
 * CloudProperties generated by hbm2java
 */
@Entity
@Table(name = "app_property", schema = "public")
public class AppProperty implements java.io.Serializable {

	private static final long serialVersionUID = -7425148284572813149L;
	private AppPropertyId id;
	private String value;

	public AppProperty() {
	}

	public AppProperty(AppPropertyId id, String value) {
		this.id = id;
		this.value = value;
	}

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "key", column = @Column(name = "key", nullable = false, length = 50)),
			@AttributeOverride(name = "profile", column = @Column(name = "profile", nullable = false, length = 50)) })
	public AppPropertyId getId() {
		return this.id;
	}

	public void setId(AppPropertyId id) {
		this.id = id;
	}

	@Column(name = "value", nullable = false, length = 2000)
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
