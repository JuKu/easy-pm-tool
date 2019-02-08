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

    @Column(name = "fixed_name", nullable = false, updatable = true)
    private boolean fixedName;

    public Group(@Size(min = 2, max = 45) String name, @Size(min = 2, max = 45) String title) {
        this(name, title, false);
    }

    public Group(@Size(min = 2, max = 45) String name, @Size(min = 2, max = 45) String title, boolean fixedName) {
        this.name = name;
        this.title = title;
        this.fixedName = fixedName;
    }

    protected Group () {
        //
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (this.fixedName) {
            throw new IllegalStateException("Cannot edit a fixed name!");
        }

        this.name = name;
    }

    public String getTitle() {
        return title;
    }

}
