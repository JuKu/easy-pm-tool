package com.jukusoft.pm.tool.def.dao;

import com.jukusoft.pm.tool.def.model.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDAO extends PagingAndSortingRepository<User, Integer> {

    @Query("SELECT u FROM com.jukusoft.pm.tool.def.model.User u where u.username = :name")
    Optional<User> findByUsername(@Param("name") String username);

}
