package univ.master.mql.memberservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import univ.master.mql.memberservice.entities.BodyMeasurements;
import univ.master.mql.memberservice.entities.Client;
import univ.master.mql.memberservice.repository.BodyMeasurementRepository;
import univ.master.mql.memberservice.repository.ClientRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class BodyMeasurementsService {

    @Autowired
    BodyMeasurementRepository repository;

    @Autowired
    ClientRepository cltRpo;
    public List<BodyMeasurements> getBodyMeasurementsByClient (Client c){
        System.err.println("API ERROR in service ::: "+c.getId());
        return repository.findByClient(c);
    }
    public BodyMeasurements getLatestBodyMeasurementByClient (Client c){
        return repository.findTopByClientOrderByTakedOnDesc(c);
    }

    public BodyMeasurements addBodyMeasurments(BodyMeasurements bodyMeasurements){
       return repository.save (bodyMeasurements);
    }

    public BodyMeasurements editBodyMeasurments(BodyMeasurements bm_new){
//        Optional<BodyMeasurements> oldOne = repository.findById(bm_new.getId());

        return repository.findById(bm_new.getId())
                .map(bm -> {
                   if(bm_new.getBiceps()!=0) bm.setBiceps(bm_new.getBiceps());
                    if(bm_new.getHeight()!=0)bm.setHeight(bm_new.getHeight());
                    if(bm_new.getWeight()!=0) bm.setWeight(bm_new.getWeight());
                    if(bm_new.getTakedOn()!=null) bm.setTakedOn(bm_new.getTakedOn());
                    return repository.save(bm);
                })
                .orElseGet(() -> {
                    return repository.save(bm_new);
                });
    }

    public void removeBodyMeasurments(Long id){
        repository.deleteById(id);
    }
    public void removeBodyMeasurmentsByClient(Client client){
         repository.removeAllByClient(client);
    }
}
