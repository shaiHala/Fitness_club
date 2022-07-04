package univ.master.mql.subscriptionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import univ.master.mql.subscriptionservice.entities.Pack;
import univ.master.mql.subscriptionservice.entities.PackageSport;

@Repository

@RepositoryRestResource(collectionResourceRel = "packageSport", path = "packageSport")
public interface PackageSportRepository extends JpaRepository<PackageSport,Long> {
    public void removeAllBySportId(Long id);
    public void removeAllByPack(Pack p);
}


