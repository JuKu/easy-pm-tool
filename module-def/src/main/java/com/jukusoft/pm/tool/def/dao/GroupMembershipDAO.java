package com.jukusoft.pm.tool.def.dao;

import com.jukusoft.pm.tool.def.model.permission.Group;
import com.jukusoft.pm.tool.def.model.permission.GroupMembership;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.IdClass;
import java.util.List;

@Repository
//@IdClass(GroupMembership.class)
public interface GroupMembershipDAO extends PagingAndSortingRepository<GroupMembership, Integer> {

    //@Query("SELECT m FROM com.jukusoft.pm.tool.def.model.permission.GroupMembership m where m.group = :groupID")
    List<GroupMembership> findByGroupID(@Param("groupID") int groupID);

}
