package com.jukusoft.pm.tool.def.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jukusoft.pm.tool.def.model.auth.AuthentificationMethod;
import com.jukusoft.pm.tool.def.model.permission.GroupMembership;
import com.jukusoft.pm.tool.def.utils.ByteUtils;
import com.jukusoft.pm.tool.def.utils.HashUtils;
import com.jukusoft.pm.tool.def.utils.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "users", indexes = {
        //@Index(columnList = "username", name = "username_idx", unique = true),
        @Index(columnList = "email", name = "email_idx"),
}, uniqueConstraints = {
        @UniqueConstraint(columnNames = "username", name = "username_uqn")
})
//@IdClass(GroupMembership.class)
public class User implements Serializable {

    //flags
    private static final int FLAG_SUPER_USER = 0;

    @Id
    @Column(name = "user_id", nullable = false, updatable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Size(max = 45)
    @Column(name = "username", unique = true, nullable = false, updatable = true)
    private String username;

    @JsonIgnore
    @Size(max = 255)
    @Column(name = "password_hash", nullable = true, updatable = true)
    private String passwordHash;

    @JsonIgnore
    @Size(max = 255)
    @Column(name = "salt", nullable = true, updatable = true)
    private String salt;

    @Size(max = 255)
    @Column(name = "email", nullable = false, updatable = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_method", nullable = false, updatable = true)
    private AuthentificationMethod authMethod;

    @Column(name = "registered", nullable = false, updatable = false)
    @CreationTimestamp
    private Date registered;

    //many options in one integer to use less memory
    @Column(name = "flags", nullable = false, updatable = true)
    private int flags;

    //@OneToOne(mappedBy = "actor", orphanRemoval = true, optional = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OneToOne(orphanRemoval = false, optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    protected Person person;

    @OneToMany (mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GroupMembership> groups = new ArrayList<>();

    public User (String username, String password, String email) {
        StringUtils.requireNonEmptyString(username);
        StringUtils.requireNonEmptyString(password);
        StringUtils.requireNonEmptyString(email);

        this.username = username;
        this.email = email;
        this.authMethod = AuthentificationMethod.DATABASE;
        this.flags = 0;

        this.setPassword(password);
    }

    /**
     * default constructor required by spring hibernate
     */
    protected User () {
        //
    }

    public void setPassword(String passwordHash) {
        if (this.authMethod != AuthentificationMethod.DATABASE) {
            throw new IllegalStateException("This user was authentificated by ldap server, so you cannot change password here.");
        }

        //generate new salt
        this.salt = HashUtils.generateSalt();

        //hash password
        this.passwordHash = HashUtils.computePasswordSHAHash(salt + passwordHash);
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getSalt() {
        return salt;
    }

    public String getEmail() {
        return email;
    }

    public Date getRegistered() {
        return registered;
    }

    public AuthentificationMethod getAuthMethod() {
        return authMethod;
    }

    public Person getPerson() {
        return person;
    }

    public String getDisplayName () {
        return this.person == null ? this.username : this.person.getFirstName() + " " + this.person.getSurName();
    }

    public boolean isSuperUser () {
        return ByteUtils.isBitSet(this.flags, FLAG_SUPER_USER);
    }

    public void setSuperUser (boolean value) {
        this.flags = ByteUtils.setBit(this.flags, FLAG_SUPER_USER, value);
    }

}
