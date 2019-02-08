package com.jukusoft.pm.tool.def.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "persons")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private int id;

    @Size(min = 2, max = 45)
    @Column(name = "first_name", nullable = false, updatable = true)
    private String firstName;

    @Size(min = 2, max = 45)
    @Column(name = "sur_name", nullable = false, updatable = true)
    private String surName;

    @OneToOne(mappedBy = "person", orphanRemoval = false, optional = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;

    public String getFirstName() {
        return firstName;
    }

    public String getSurName() {
        return surName;
    }

    public User getUser() {
        return user;
    }

}
