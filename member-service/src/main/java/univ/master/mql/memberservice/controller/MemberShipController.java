package univ.master.mql.memberservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import univ.master.mql.memberservice.entities.Client;
import univ.master.mql.memberservice.entities.MemberShip;
import univ.master.mql.memberservice.services.MemberShipService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController

@Slf4j
@RequestMapping("/api/member/memberships/")
public class MemberShipController {
    @Autowired
    private MemberShipService service;
    @GetMapping(path="/byClient")
    public List<MemberShip> getByClient(@RequestParam int id){
        Client c=new Client();
        c.setId(Long.parseLong(""+id));
        return service.getMembershipByClient(c);
    }
    @GetMapping(path="/all")
    public List<MemberShip> getAll(){
        return service.getMemberships();
    }
    @GetMapping("/")
    public Optional<MemberShip>  getById(@RequestParam Long id){
        return service.getMembershipById(id);
    }
    @PostMapping(path="add", consumes= MediaType.APPLICATION_JSON_VALUE)
    public  String add(@RequestBody MemberShip ms){
        System.err.println(ms.toString());
        if( service.addMembership(ms)!=null){
            return "New membership affected to  "+ms.getClient();
        }
        else {
            return "Error :: while adding membership";
        }
    }
    @PutMapping("/edit")
    public String edit(@RequestBody MemberShip ms){
//        return ms.toString();
        if( service.editMembership(ms)!=null){
//            return ms.getPackageId().toString();
            return "Membership record has been updated for "+ms.getClient();
        }
        else {
            return "Error :: while updating  membership record";
        }
    }

    @GetMapping("/removeByClient")
    public void removeByClient(@RequestParam Long clientId){
        Client c= new Client();
        c.setId(clientId);
        service.removeMembershipByClient(c);
    }

    @GetMapping("/remove")
    public void remove(@RequestParam Long id){
        service.removeMembership(id);
    }

    @GetMapping("/valid")
    public List<MemberShip> getValidMembership() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDateTime now = LocalDateTime.now();
        Instant instant = now.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        return service.getMemberships().stream().filter(membership -> membership.getEndDate().after(date))
               .collect(Collectors.toList());

    }
    @GetMapping("/expired")
    public List<MemberShip> getExpiredMembership() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDateTime now = LocalDateTime.now();
        Instant instant = now.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        return service.getMemberships().stream().filter(membership -> membership.getEndDate().before(date))
                .collect(Collectors.toList());

    }
    @GetMapping(path="/byValidClient")
    public List<MemberShip> getValidByClient(@RequestParam int id){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        Instant instant = now.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);

        Client c=new Client();
        c.setId(Long.parseLong(""+id));
        return service.getMembershipByClient(c).stream().filter(membership -> membership.getEndDate().after(date))
                .collect(Collectors.toList());
    }
}
