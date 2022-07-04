package univ.master.mql.memberservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import univ.master.mql.memberservice.entities.Attendance;
import univ.master.mql.memberservice.entities.Client;

import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "attendance", path = "attendance")
public interface AttendanceRepository extends JpaRepository<Attendance,Long> {
    public List<Attendance> findByClient(Client client);
    public void removeAllByClient(Client c);
}
