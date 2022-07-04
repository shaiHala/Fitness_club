package univ.master.mql.subscriptionservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import univ.master.mql.subscriptionservice.dto.SportDTO;
import univ.master.mql.subscriptionservice.entities.Pack;
import univ.master.mql.subscriptionservice.entities.PackageSport;
import univ.master.mql.subscriptionservice.feign.SportPackageProxy;
import univ.master.mql.subscriptionservice.repository.PackageSportRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class PackageSportService {
	  @Autowired
	  private PackageSportRepository repo ;


	private final SportPackageProxy proxy;

	public PackageSportService(SportPackageProxy proxy) {
		this.proxy = proxy;
	}


	public PackageSport addSportPackage(PackageSport sp) {
          return  repo.save(sp);
      }
	  public void removeGroupById(Long id){
		  repo.deleteById(id);
	  }
	  public void removeAllBySport(Long id){
		  repo.removeAllBySportId(id);
	  }
	  public void removeByPackage(Pack pack){
		repo.removeAllByPack(pack);
	  }
	  public Optional<PackageSport> setSport(Long s, Long id){
		  SportDTO sport= new SportDTO();
		  sport.setId(s);
		  proxy.setSport(sport,id);
		  return repo.findById(id)
				  .map(sp -> {
					  sp.setSportId(s);
					  return repo.save(sp);
				  });
	  }
	  public Optional<PackageSport> setPackage(Pack p1, Long id){

		 proxy.setPackage(p1.getId(),id);
		 return repo.findById(id)
				.map(sp -> {
					sp.setPack(p1);
					return repo.save(sp);
				});
	  }
	  public Optional<PackageSport> setNbCourse(Long id , int nb){
		 proxy.setNbCourses(nb, id);
		 return repo.findById(id)
				  .map(sp -> {
					  sp.setNbOfCourses(nb);
					  return repo.save(sp);
				  });
	  }

}
