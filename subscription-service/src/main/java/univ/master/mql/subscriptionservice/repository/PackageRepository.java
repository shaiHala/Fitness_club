package univ.master.mql.subscriptionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import univ.master.mql.subscriptionservice.entities.Pack;

import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "package", path = "package")
public interface PackageRepository extends JpaRepository<Pack,Long> {
    public List<Pack> findAllByPackageType(String pack);
    public void removeAllByPackageType(String p);
}
