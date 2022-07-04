package univ.master.mql.memberservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import univ.master.mql.memberservice.entities.Client;
import univ.master.mql.memberservice.entities.MemberShip;

import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "membership", path = "membership")
public interface MemberShipRepository extends JpaRepository<MemberShip,Long> {
    public List<MemberShip> findByClient(Client client);
    public void removeAllByClient(Client c);
}
