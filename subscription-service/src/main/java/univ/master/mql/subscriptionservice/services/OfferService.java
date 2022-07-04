package univ.master.mql.subscriptionservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import univ.master.mql.subscriptionservice.entities.Offer;
import univ.master.mql.subscriptionservice.entities.Pack;
import univ.master.mql.subscriptionservice.repository.OfferRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OfferService {
    @Autowired
    private OfferRepository repository;

    public Optional<Offer> getOfferById (Long id){
        return repository.findById(id);
    }
    public List<Offer> getOffers (){
        return repository.findAll();
    }
    public List<Offer> getOffersByPackage (Pack p){
        return repository.findAllByPack(p);
    }

    public Offer editOffer(Offer offer_new){

        return repository.findById(offer_new.getId())
                .map(ms -> {
//                    if(ms_new.getPackageId()!=0) bm.getPackageId(ms_new.getPackageId());
                    if(offer_new.getStartDate()!=null)ms.setStartDate(offer_new.getStartDate());
                    if(offer_new.getEndDate()!=null)ms.setEndDate(offer_new.getEndDate());
                    if(offer_new.getPack()!=null)ms.setPack(offer_new.getPack());

                    return repository.save(ms);
                })
                .orElseGet(() -> {
                    return repository.save(offer_new);
                });
    }

    public Offer add(Offer offer){
        return  repository.save(offer);
    }
    public void removeOffer(Long id){
        repository.deleteById(id);
    }
    public void removeOfferByPack(Pack p){
        repository.removeAllByPack(p);
    }
}
