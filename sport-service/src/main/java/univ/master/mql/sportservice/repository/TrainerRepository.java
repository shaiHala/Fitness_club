package univ.master.mql.sportservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import univ.master.mql.sportservice.entities.Sport;
import univ.master.mql.sportservice.entities.Trainer;

import java.util.List;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer,Long> {
    public List<Trainer> findTrainersByAvailabilityTrue();
//    public Trainer findByAvailabilityTrue();
    public List<Trainer> findTrainersByAvailabilityFalse();

}
