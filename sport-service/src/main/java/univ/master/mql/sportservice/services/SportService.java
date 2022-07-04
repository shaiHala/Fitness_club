package univ.master.mql.sportservice.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import univ.master.mql.sportservice.entities.Collectif;
import univ.master.mql.sportservice.entities.Individual;
import univ.master.mql.sportservice.entities.Sport;
import univ.master.mql.sportservice.repository.SportRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SportService {
	  @Autowired
	  private SportRepository sportRepo;

	  public void addIndividualSport(Individual sport) {
		  sportRepo.save(sport);
	 }
	  public List<Sport> getSports(){
		 List<Sport> sports= sportRepo.findAll();
		 return sports;
	  }
	  public Optional<Sport> getSport(Long id){
		  return sportRepo.findById(id);
	  }
	  public int removeSportById(Long id){
		  return sportRepo.deleteAllById(id);
	  }

	public boolean withTrainer (Long id){
		Individual c=(Individual)sportRepo.findById(id).get();
//		 c.getWithTrainer();
		 System.err.println(c.toString());
		 return  c.getWithTrainer();
	}


	public void addCollectiveSprot(Collectif s){
		   sportRepo.save(s);
	}

}