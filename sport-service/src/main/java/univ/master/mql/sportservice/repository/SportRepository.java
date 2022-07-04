package univ.master.mql.sportservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import univ.master.mql.sportservice.entities.Sport;
import univ.master.mql.sportservice.services.SportService;

@Repository
public interface SportRepository extends JpaRepository<Sport,Long> {


    int deleteAllById(Long id);
//    @Query(value = "SELECT with_trainer FROM sport WHERE id  = :id",nativeQuery = true)
//    public WithTrainer withTrainer(@Param("id") Long id);
//    public interface WithTrainer{
//        boolean withTrainer();
//    }
}
