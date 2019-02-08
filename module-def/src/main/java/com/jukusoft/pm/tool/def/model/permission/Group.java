package com.jukusoft.pm.tool.def.model.permission;

import com.jukusoft.pm.tool.def.utils.ByteUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id", nullable = false, updatable = false)
    protected int id;

    @Size(min = 2, max = 45)
    @Column(name = "name", nullable = false, updatable = true, unique = true)
    private String name;

    @Size(min = 2, max = 45)
    @Column(name = "title", nullable = false, updatable = true)
    private String title;

    public Group(@Size(min = 2, max = 45) String name, @Size(min = 2, max = 45) String title) {
        this.name = name;
        this.title = title;
    }

    protected Group () {
        //
    }

}
