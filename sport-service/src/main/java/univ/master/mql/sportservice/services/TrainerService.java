package univ.master.mql.sportservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import univ.master.mql.sportservice.entities.Collectif;
import univ.master.mql.sportservice.entities.Sport;
import univ.master.mql.sportservice.entities.Trainer;
import univ.master.mql.sportservice.repository.SportRepository;
import univ.master.mql.sportservice.repository.TrainerRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TrainerService {
	  @Autowired
	  private TrainerRepository trainerRepo;


	  public void addTrainer(Trainer trainer) {
		  trainerRepo.save(trainer);
	 }
	  public List<Trainer> getTrainers(){
		 List<Trainer> trianers= trainerRepo.findAll();
		 return trianers;
	  }
	  public Optional<Trainer> getTrainer(Long id){
		  return trainerRepo.findById(id);
	  }
	  public Trainer editTrainer (Trainer trainer){
		  System.err.println(trainer.getId());
		return trainerRepo.findById(trainer.getId())
				.map(tr -> {
					System.err.println("Henaaa ... >"+tr.getId());
					if(trainer.getLName()!="") tr.setLName(trainer.getLName());
					if(trainer.getFName()!="")tr.setFName(trainer.getFName());
					if(trainer.getDateOfBirth()!=null)tr.setDateOfBirth(trainer.getDateOfBirth());
					if(trainer.getCin()!="")tr.setCin(trainer.getCin());
					if(trainer.getGender()!="")tr.setGender(trainer.getGender());
					if(trainer.getAvailability()!= tr.getAvailability())tr.setAvailability(trainer.getAvailability());
					if(trainer.getAddress()!="")tr.setAddress(trainer.getAddress());
					if(trainer.getEmail()!="")tr.setEmail(trainer.getEmail());
					if(trainer.getPhone()!="")tr.setPhone(trainer.getPhone());
					if(trainer.getUsername()!="")tr.setUsername(trainer.getUsername());
					if(trainer.getPassword()!="")tr.setPassword(trainer.getPassword());
					return trainerRepo.save(trainer);
				})
				.orElseGet(() -> {
					return trainerRepo.save(trainer);
				});
	  }
	  public Optional<Trainer> setTrainerAvailability(Long id , Boolean availible){
		  return trainerRepo.findById(id)
				  .map(trainer -> {
					  trainer.setAvailability(availible);
					  return trainerRepo.save(trainer);
				  });
	  }
	  public List<Trainer> getAvailibleTrainers(){
		return trainerRepo.findTrainersByAvailabilityTrue();
	  }
	  public List<Trainer> getUnvailibleTrainers(){
		return trainerRepo.findTrainersByAvailabilityFalse();
	  }
	  public void removeTrainerById(Long id){
		   trainerRepo.deleteById(id);
	  }
	  public Optional<Trainer> changeSport(Collectif sport, Long id){
		  System.err.println("sport :: "+sport.getId() +"    -> id:: "+id);
		  System.err.println(trainerRepo.findById(id).get().getFName());
		  return trainerRepo.findById(id)
				  .map(trainer -> {
					  trainer.setSport(sport);
					  System.err.println(trainer.toString());
					  return trainerRepo.save(trainer);
				  });
	  }


}
