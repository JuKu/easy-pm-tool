package com.jukusoft.pm.tool.def.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jukusoft.pm.tool.def.model.auth.AuthentificationMethod;
import com.jukusoft.pm.tool.def.utils.HashUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "users", indexes = {
        //@Index(columnList = "username", name = "username_idx", unique = true),
        @Index(columnList = "email", name = "email_idx"),
}, uniqueConstraints = {
        @UniqueConstraint(columnNames = "username", name = "username_uqn")
})
public class User {

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

    public User (String username, String password, String email) {
        this.username = username;
        this.email = email;
        this.authMethod = AuthentificationMethod.DATABASE;

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

}
