package univ.master.mql.subscriptionservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import univ.master.mql.subscriptionservice.dto.SportDTO;
import univ.master.mql.subscriptionservice.dto.SportPackageDTO;
import univ.master.mql.subscriptionservice.entities.Pack;
import univ.master.mql.subscriptionservice.entities.PackageSport;
import univ.master.mql.subscriptionservice.feign.SportPackageProxy;
import univ.master.mql.subscriptionservice.repository.PackageRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PackageService {
    @Autowired
    private PackageRepository repository;

    @Autowired
    private  PackageSportService service;
    private final SportPackageProxy proxy;

    public PackageService(SportPackageProxy proxy) {
        this.proxy = proxy;
    }


    public List<Pack> getPackagesByType (String packType){
        return repository.findAllByPackageType(packType);
    }

    public List<Pack> getPacks (){
        return repository.findAll();
    }

    public Optional<Pack> getPack (Long id){
        return repository.findById(id);
    }

    public Pack add(Pack pack, Long idSport, int nbCourses){
        Pack pack1=repository.save(pack);
        System.err.println("Pack Id"+pack1.getId());
        PackageSport ps= new PackageSport();
        ps.setPack(pack1);
        ps.setSportId(idSport);
        ps.setNbOfCourses(nbCourses);
        PackageSport packSport= service.addSportPackage(ps);
        System.err.println("PackSport"+packSport);
        SportDTO s=new SportDTO();
        s.setId(idSport);
        SportPackageDTO sp=new SportPackageDTO(packSport.getId(),s, pack1.getId(),nbCourses);
        System.err.println(sp);
        proxy.addSportPackage(sp);
        return pack1;
    }

    public Pack edit(Pack pack_new){

        return repository.findById(pack_new.getId())
                .map(pack -> {
                    if(pack_new.getDetails()!="")pack.setDetails(pack_new.getDetails());
                    if(pack_new.getPrix()!=0)pack.setPrix(pack_new.getPrix());
                    if(pack_new.getDuration()!="") pack.setDuration(pack_new.getDuration());
                    if(pack_new.getStatus()!=pack.getStatus()) pack.setStatus(pack_new.getStatus());
                    if(pack_new.getPayementType()!="") pack.setPayementType(pack_new.getPayementType());
                    if(pack_new.getPackageType()!="") pack.setPackageType(pack_new.getPackageType());
                    return repository.save(pack);
                })
                .orElseGet(() -> {
                    return repository.save(pack_new);
                });
    }

    public void remove(Long id){
        repository.deleteById(id);
    }

    public void removeAllByPackageType(String p){
        repository.removeAllByPackageType(p);
    }


    public Pack enable (Long id){
        Pack pack=repository.findById(id).get();
        pack.setStatus(true);
        return repository.save(pack);
    }
    public Pack disable (Long id){
        Pack pack=repository.findById(id).get();
        pack.setStatus(false);
        return repository.save(pack);
    }
}
