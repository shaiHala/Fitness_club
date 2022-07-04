package univ.master.mql.memberservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import univ.master.mql.memberservice.entities.BodyMeasurements;
import univ.master.mql.memberservice.entities.Client;

import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "bodyMeasurement", path = "bodyMeasurement")
public interface BodyMeasurementRepository extends JpaRepository <BodyMeasurements,Long> {
            public List<BodyMeasurements> findByClient (Client client);
            public BodyMeasurements  findTopByClientOrderByTakedOnDesc(Client client);
            public void removeAllByClient(Client client);

}
