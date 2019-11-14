package net.potm.business.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "person", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Person implements java.io.Serializable {

    // Status values
    public static final int STATUS_NEW = 0;
    public static final int STATUS_DISABLED = 1;
    public static final int STATUS_ACTIVE = 2;


    private static final long serialVersionUID = 472473363307502523L;

    private Long id;
    private long version;
    private Date created_on;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String pwdSalt;
    private Date updated_on;
    private String authCode;
    private long status;
    private long activationEmailCount;
    private Set<Address> addresses = new HashSet<>(0);
    private Set<Role> roles = new HashSet<>(0);


    public Person() {
    }

    public Person(long id) {
        this.id = id;
    }

    public Person(Long id, Date created, String email, String password, String pwdSalt, Date updated_on, String authCode,
                  long status, long activationEmailCount) {
        this.id = id;
        this.created_on = created;
        this.email = email;
        this.password = password;
        this.pwdSalt = pwdSalt;
        this.updated_on = updated_on;
        this.authCode = authCode;
        this.status = status;
        this.activationEmailCount = activationEmailCount;
    }

    public Person(Long id, Date created, String email, String firstName, String lastName, String password,
                  String pwdSalt, Date updated_on, String authCode, long status, long activationEmailCount,
                  Set<Address> addresses, Set<Role> roles) {
        this.id = id;
        this.created_on = created;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.pwdSalt = pwdSalt;
        this.updated_on = updated_on;
        this.authCode = authCode;
        this.status = status;
        this.activationEmailCount = activationEmailCount;
        this.addresses = addresses;
        this.roles = roles;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Version
    @Column(name = "version", nullable = false)
    public long getVersion() {
        return this.version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on", nullable = false, length = 29)
    public Date getCreated_on() {
        return this.created_on;
    }

    public void setCreated_on(Date created) {
        this.created_on = created;
    }

    @Column(name = "email", unique = true, nullable = false, length = 50)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "first_name", length = 100)
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", length = 100)
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "pwd_salt", nullable = false)
    public String getPwdSalt() {
        return this.pwdSalt;
    }

    public void setPwdSalt(String pwdSalt) {
        this.pwdSalt = pwdSalt;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_on", nullable = false, length = 29)
    public Date getUpdated_on() {
        return this.updated_on;
    }

    public void setUpdated_on(Date updated) {
        this.updated_on = updated;
    }

    @Column(name = "auth_code", nullable = false, length = 50)
    public String getAuthCode() {
        return this.authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    @Column(name = "status", nullable = false)
    public long getStatus() {
        return this.status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    @Column(name = "activation_email_count", nullable = false)
    public long getActivationEmailCount() {
        return this.activationEmailCount;
    }

    public void setActivationEmailCount(long activationEmailCount) {
        this.activationEmailCount = activationEmailCount;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person")
    public Set<Address> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "person_role", schema = "public", joinColumns = {
            @JoinColumn(name = "person", nullable = false, updatable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "role", nullable = false, updatable = false)})
    public Set<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String toString() {
        return String.format("%d-%s", this.id, this.email);
    }
}
