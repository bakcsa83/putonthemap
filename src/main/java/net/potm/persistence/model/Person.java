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

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
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
    private Date createdOn;
    private String email;
    private String nickName;
    private String firstName;
    private String lastName;
    private String password;
    private String pwdSalt;
    private Date updatedOn;
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

    public Person(String email, String nickName, String firstName, String lastName, String password, String pwdSalt, String authCode,
                  long status, long activationEmailCount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.pwdSalt = pwdSalt;
        this.authCode = authCode;
        this.status = status;
        this.activationEmailCount = activationEmailCount;
    }

    public Person(Long id, Date created, String email, String password, String pwdSalt, Date updatedOn, String authCode,
                  long status, long activationEmailCount) {
        this.id = id;
        this.createdOn = created;
        this.email = email;
        this.password = password;
        this.pwdSalt = pwdSalt;
        this.updatedOn = updatedOn;
        this.authCode = authCode;
        this.status = status;
        this.activationEmailCount = activationEmailCount;
    }

    public Person(Long id, Date created, String email, String firstName, String lastName, String password,
                  String pwdSalt, Date updatedOn, String authCode, long status, long activationEmailCount,
                  Set<Address> addresses, Set<Role> roles) {
        this.id = id;
        this.createdOn = created;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.pwdSalt = pwdSalt;
        this.updatedOn = updatedOn;
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
    public Date getCreatedOn() {
        return this.createdOn;
    }

    public void setCreatedOn(Date created) {
        this.createdOn = created;
    }

    @Column(name = "email", unique = true, nullable = false, length = 50)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "nick_name", length = 100, unique = true)
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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
    public Date getUpdatedOn() {
        return this.updatedOn;
    }

    public void setUpdatedOn(Date updated) {
        this.updatedOn = updated;
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

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", version=" + version +
                ", createdOn=" + createdOn +
                ", email='" + email + '\'' +
                ", nickName='" + nickName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", pwdSalt='" + pwdSalt + '\'' +
                ", updatedOn=" + updatedOn +
                ", authCode='" + authCode + '\'' +
                ", status=" + status +
                ", activationEmailCount=" + activationEmailCount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) &&
                Objects.equals(email, person.email) &&
                Objects.equals(nickName, person.nickName) &&
                Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, nickName, firstName, lastName);
    }
}
