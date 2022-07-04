package univ.master.mql.sportservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import univ.master.mql.sportservice.entities.Sport;
import univ.master.mql.sportservice.entities.SportPackage;

@Repository
public interface SportPackageRepository extends JpaRepository<SportPackage,Long> {
    public void removeAllBySport(Sport sport);
    public void removeAllByPackageId(Long id);
}
