package univ.master.mql.memberservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import univ.master.mql.memberservice.entities.Client;
import univ.master.mql.memberservice.entities.MemberShip;
import univ.master.mql.memberservice.repository.ClientRepository;
import univ.master.mql.memberservice.repository.MemberShipRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberShipService {
    @Autowired
    private MemberShipRepository repository;
    @Autowired
    private ClientRepository cltRpo;

    public List<MemberShip> getMembershipByClient (Client c){
        return repository.findByClient(c);
    }
    public List<MemberShip> getMemberships (){
        return repository.findAll();
    }
    public Optional<MemberShip> getMembershipById (Long id){
        return repository.findById(id);
    }

    public MemberShip addMembership(MemberShip memberShip){
        return repository.save (memberShip);
    }

    public MemberShip editMembership(MemberShip ms_new){

        return repository.findById(ms_new.getId())
                .map(ms -> {
//                    if(ms_new.getPackageId()!=0) bm.getPackageId(ms_new.getPackageId());
                    if(ms_new.getNote()!=null)ms.setNote(ms_new.getNote());
                    if(ms_new.getPackageId()!=0)ms.setPackageId(ms_new.getPackageId());
                    if(ms_new.getStartDate()!=null) ms.setStartDate(ms_new.getStartDate());
                    if(ms_new.getEndDate()!=null) ms.setEndDate(ms_new.getEndDate());
                    return repository.save(ms);
                })
                .orElseGet(() -> {
                    return repository.save(ms_new);
                });
    }

    public void removeMembership(Long id){
        repository.deleteById(id);
    }
    public void removeMembershipByClient(Client client){
        repository.removeAllByClient(client);
    }
}
