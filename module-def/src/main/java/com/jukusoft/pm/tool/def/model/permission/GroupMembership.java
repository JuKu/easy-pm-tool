package com.jukusoft.pm.tool.def.model.permission;

import com.jukusoft.pm.tool.def.model.User;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "group_members", indexes = {
        @Index(columnList = "begins_at", name = "begins_at_idx"),
        @Index(columnList = "ends_at", name = "ends_at_idx")
})
public class GroupMembership {

    @Id
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false, updatable = false)
    private Group group;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @Column(name = "is_leader", nullable = false, updatable = true)
    private boolean isLeader = false;

    @Column(name = "begins_at", nullable = true, updatable = true)
    private Date beginsAt;

    @Column(name = "ends_at", nullable = true, updatable = true)
    private Date endsAt;

    protected GroupMembership () {
        //
    }

    public GroupMembership (Group group, User user) {
        Objects.requireNonNull(group);
        Objects.requireNonNull(user);

        this.group = group;
        this.user = user;
    }

    public Group getGroup() {
        return group;
    }

    public User getUser() {
        return user;
    }

    public boolean isLeader() {
        return isLeader;
    }

    public void setLeader(boolean leader) {
        isLeader = leader;
    }

    public Date getBeginsAt() {
        return beginsAt;
    }

    public void setBeginsAt(Date beginsAt) {
        this.beginsAt = beginsAt;
    }

    public Date getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(Date endsAt) {
        this.endsAt = endsAt;
    }

    public boolean isMember () {
        Date current = new Date();

        //check, if beginsAt <= current
        if (beginsAt != null && !current.after(beginsAt)) {
            return false;
        }

        if (endsAt != null && !current.before(endsAt)) {
            return false;
        }

        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupMembership that = (GroupMembership) o;
        return group.equals(that.group) &&
                user.equals(that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, user);
    }

}
