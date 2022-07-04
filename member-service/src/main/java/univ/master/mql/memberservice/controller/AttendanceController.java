package univ.master.mql.memberservice.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import univ.master.mql.memberservice.entities.Attendance;
import univ.master.mql.memberservice.entities.Client;
import univ.master.mql.memberservice.services.AttendanceService;

import java.util.List;
import java.util.Optional;

@RestController

@Slf4j
@RequestMapping("/api/member/attendance/")
public class AttendanceController {
    @Autowired
    private AttendanceService service;
    @GetMapping(path="/byClient")
    public List<Attendance> getByClient(@RequestParam int id){
        Client c=new Client();
        c.setId(Long.parseLong(""+id));
        return service.getAttendanceByClient(c);
    }
    @GetMapping(path="/all")
    public List<Attendance> getAll(){
        return service.getAllAttendance();
    }
    @GetMapping("/")
    public Optional<Attendance> getById(@RequestParam Long id){
        return service.getAttedanceById(id);
    }
    @PostMapping(path="add", consumes= MediaType.APPLICATION_JSON_VALUE)
    public  String add(@RequestBody Attendance att){
        System.err.println(att.toString());
        if( service.addMembership(att)!=null){
            return "New membership affected to  "+att.getClient();
        }
        else {
            return "Error :: while adding membership";
        }
    }
    @PutMapping("/edit")
    public String edit(@RequestBody Attendance att){
        if( service.editMembership(att)!=null){
            return "Membership record has been updated for "+att.getClient();
        }
        else {
            return "Error :: while updating  membership record";
        }
    }

    @GetMapping("/removeByClient")
    public void removeByClient(@RequestParam Long clientId){
        Client c= new Client();
        c.setId(clientId);
        service.removeAttendanceByClient(c);
    }

    @GetMapping("/remove")
    public void remove(@RequestParam Long id){
        service.removeAttendance(id);
    }

}
