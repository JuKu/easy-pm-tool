package com.jukusoft.pm.tool.def.model.permission;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "permissions")
public class Permission {

    @Id
    @Size(max = 50)
    @Column(name = "token", nullable = false, updatable = false)
    private String token;

    @Size(min = 2, max = 45)
    @Column(name = "title", nullable = false, updatable = true, unique = true)
    private String title;

    public Permission(@Size(max = 50) String token, @Size(min = 2, max = 45) String title) {
        this.token = token;
        this.title = title;
    }

    protected Permission () {
        //
    }

    public String getToken() {
        return token;
    }

    public String getTitle() {
        return title;
    }

}
