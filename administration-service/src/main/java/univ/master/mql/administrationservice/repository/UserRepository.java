package univ.master.mql.administrationservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import univ.master.mql.administrationservice.entity.User;

import java.util.Optional;

@Repository
@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UserRepository extends JpaRepository<User, Long> {
    public void deleteUserByUsername(String username);
    public void deleteUserById(String id);
    public Optional<User> findUserByUsername(String username);
    public Optional<User> findUserById(String id);
}
