package univ.master.mql.sportservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import univ.master.mql.sportservice.entities.Collectif;
import univ.master.mql.sportservice.entities.Group;
import univ.master.mql.sportservice.entities.Sport;
import univ.master.mql.sportservice.entities.Trainer;
import univ.master.mql.sportservice.repository.GroupRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GroupService {
	  @Autowired
	  private GroupRepository groupRepo;

	  public void addGroup(Group group) {
		  groupRepo.save(group);
	 }
	  public List<Group> getGroups(){
		 List<Group> groups= groupRepo.findAll();
		 return groups;
	  }
	  public Optional<Group> getGroup(Long id){
		  return groupRepo.findById(id);
	  }
	  public void removeGroupById(Long id){
		   groupRepo.deleteById(id);
	  }
	  public void removeGroupBySport(Sport sport){
			groupRepo.deleteAllBySport(sport);
	  }
	  public Optional<Group> setTrainer(Trainer trainer, Long id){
		  return groupRepo.findById(id)
				  .map(group -> {
					  group.setTrainer(trainer);
					  return groupRepo.save(group);
				  });
	  }
	  public Optional<Group> setSport(Collectif sport, Long id){
		  return groupRepo.findById(id)
				  .map(group -> {
					  group.setSport(sport);
					  System.err.println(group.getSport());
					  return groupRepo.save(group);
				  });

	}
}
