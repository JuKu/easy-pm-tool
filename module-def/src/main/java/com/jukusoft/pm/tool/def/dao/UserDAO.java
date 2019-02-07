package com.jukusoft.pm.tool.def.dao;

import com.jukusoft.pm.tool.def.model.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface UserDAO extends PagingAndSortingRepository<User, Integer> {

    @Override
    public User save(User user);

    @Cacheable("user-findAll")
    @Override
    public List<User> findAll();

    /**
     * Retrieves an entity by its id.
     *
     * @param userID must not be {@literal null}.
     * @return the entity with the given id or {@literal Optional#empty()} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}.
     */
    Optional<User> findById(int userID);

    Optional<User> findByUsername(String username);

    @Override
    public long count();

}
