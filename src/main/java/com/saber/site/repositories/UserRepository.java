package com.saber.site.repositories;

import com.saber.site.model.UserPrincipal;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserPrincipal,Long> {
    UserPrincipal findByUsername(String username);
}
