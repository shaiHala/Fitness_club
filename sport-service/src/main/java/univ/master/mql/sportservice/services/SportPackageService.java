package univ.master.mql.sportservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import univ.master.mql.sportservice.entities.*;
import univ.master.mql.sportservice.repository.GroupRepository;
import univ.master.mql.sportservice.repository.SportPackageRepository;
import univ.master.mql.sportservice.repository.SportRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SportPackageService {
	  @Autowired
	  private SportPackageRepository repo ;

	@Autowired
	private SportRepository sportrepo ;

	  public void addSportPackage(SportPackage sp) {
		  System.err.println(sp.getSport());
		  sp.setSport(sportrepo.findById(sp.getSport().getId()).get());
		  repo.save(sp);
	 }

	  public void removeGroupById(Long id){
		  repo.deleteById(id);
	  }
	  public void removeAllBySport(Sport sport){
		  repo.removeAllBySport(sport);
	  }
	  public void removeByPackage(Long id_package){
		repo.removeAllByPackageId(id_package);
	  }

	  public Optional<SportPackage> setSport(Sport s, Long id){
		  return repo.findById(id)
				  .map(sp -> {
					  sp.setSport(s);
					  return repo.save(sp);
				  });
	  }
	  public Optional<SportPackage> setPackage(Long id_package, Long id){
		 return repo.findById(id)
				.map(sp -> {
					sp.setPackageId(id_package);
					return repo.save(sp);
				});
	  }
	  public Optional<SportPackage> setNbCourse(Long id , int nb){
		  return repo.findById(id)
				  .map(sp -> {
					  sp.setNbOfCourses(nb);
					  return repo.save(sp);
				  });
	  }

}
