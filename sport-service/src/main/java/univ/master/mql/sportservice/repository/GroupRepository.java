package univ.master.mql.sportservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import univ.master.mql.sportservice.entities.Group;
import univ.master.mql.sportservice.entities.Sport;

@Repository
public interface GroupRepository extends JpaRepository<Group,Long> {

    public void deleteAllBySport(Sport sport);
}
