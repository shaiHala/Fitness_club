package univ.master.mql.memberservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import univ.master.mql.memberservice.entities.Attendance;
import univ.master.mql.memberservice.entities.Client;
import univ.master.mql.memberservice.repository.AttendanceRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AttendanceService {
    @Autowired
    AttendanceRepository repository;
    public List<Attendance> getAttendanceByClient (Client c){
        return repository.findByClient(c);
    }
    public List<Attendance> getAllAttendance (){
        return repository.findAll();
    }
    public Optional<Attendance> getAttedanceById (Long id){
        return repository.findById(id);
    }

    public Attendance addMembership(Attendance attendance){
        return repository.save (attendance);
    }

    public Attendance editMembership(Attendance att_new){

        return repository.findById(att_new.getId())
                .map(att -> {
                    if(att_new.getDate()!=null)att.setDate(att_new.getDate());
                    if(att_new.getTime()!=null)att.setTime(att_new.getTime());
                    return repository.save(att);
                })
                .orElseGet(() -> {
                    return repository.save(att_new);
                });
    }

    public void removeAttendance(Long id){
        repository.deleteById(id);
    }
    public void removeAttendanceByClient(Client client){
        repository.removeAllByClient(client);
    }
}
