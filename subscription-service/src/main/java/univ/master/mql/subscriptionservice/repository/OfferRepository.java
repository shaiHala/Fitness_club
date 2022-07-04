package univ.master.mql.subscriptionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import univ.master.mql.subscriptionservice.entities.Offer;
import univ.master.mql.subscriptionservice.entities.Pack;

import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "offer", path = "offer")
public interface OfferRepository extends JpaRepository<Offer,Long> {
    public List<Offer> findAllByPack(Pack pack);
    public void removeAllByPack(Pack pack);
}
