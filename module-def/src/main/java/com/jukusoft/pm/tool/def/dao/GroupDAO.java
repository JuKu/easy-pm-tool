package com.jukusoft.pm.tool.def.dao;

import com.jukusoft.pm.tool.def.model.permission.Group;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupDAO extends PagingAndSortingRepository<Group, Integer> {

    @Query("SELECT g FROM com.jukusoft.pm.tool.def.model.permission.Group g where g.name = :name")
    Optional<Group> findByName(@Param("name") String name);

}
