package com.jukusoft.pm.tool.def.model.permission;

import com.jukusoft.pm.tool.def.model.User;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "groups")
public class Group implements Serializable {

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

    @OneToMany(mappedBy = "groupID", orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<GroupMembership> members = new ArrayList<>();

    @ElementCollection(targetClass=String.class)
    @JoinTable(name = "group_permissions",
            joinColumns = @JoinColumn(name = "group_id"))
    @MapKeyColumn(name = "token")
    @Column(name = "value")
    private Set<Permission> permissions;

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

    public int getId() {
        return id;
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

    public List<GroupMembership> listMemberships() {
        return members;
    }

    /*public GroupMembership addMember (User user) {
        GroupMembership membership = new GroupMembership(this, user);
        this.members.add(membership);

        return membership;
    }

    public void removeMember (User user) {
        this.members.remove(new GroupMembership(this, user));
    }*/

    public void addMembership (GroupMembership membership) {
        this.members.add(membership);
    }

    public List<User> listMembers() {
        List<User> Users = new ArrayList<>();

        for (GroupMembership membership : listMemberships()) {
            if (membership.isMember()) {
                Users.add(membership.getUserID());
            }
        }

        return Users;
    }

    public Collection<Permission> listPermissions() {
        return permissions;
    }
}
