package univ.master.mql.memberservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import univ.master.mql.memberservice.entities.BodyMeasurements;
import univ.master.mql.memberservice.entities.Client;
import univ.master.mql.memberservice.services.BodyMeasurementsService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/member/bodyMeasurements/")
public class BodyMeasurementsController {
    @Autowired
    private BodyMeasurementsService service;
    @GetMapping(path="/byClient")
    public List<BodyMeasurements> getByClient(@RequestParam int id){
        Client c=new Client();
        c.setId(Long.parseLong(""+id));
        return service.getBodyMeasurementsByClient(c);
    }
    @GetMapping("/latestbyClient")
    public  BodyMeasurements getLatestByClient(@RequestParam Long id){
        Client c=new Client();
        c.setId(Long.parseLong(""+id));
        return service.getLatestBodyMeasurementByClient(c);
    }
    @PostMapping(path="add", consumes=MediaType.APPLICATION_JSON_VALUE)
    public  String add(@RequestBody BodyMeasurements bm){
        System.err.println(bm.toString());
        if( service.addBodyMeasurments(bm)!=null){
            return "New body measurement record has been added for "+bm.getClient();
        }
        else {
            return "Error :: while adding  body measurement record";
        }
    }
    @PutMapping("/edit")
    public String edit(@RequestBody BodyMeasurements bm){
//        return bm.toString();
        if( service.editBodyMeasurments(bm)!=null){
            return "body measurement record has been updated for "+bm.getClient();
        }
        else {
            return "Error :: while updating  body measurement record";
        }
    }

    @GetMapping("/removeByClient")
    public void removeByClient(@RequestParam Long clientId){
        Client c= new Client();
        c.setId(clientId);
        service.removeBodyMeasurmentsByClient(c);
    }

    @GetMapping("/remove")
    public void remove(@RequestParam Long id){
       service.removeBodyMeasurments(id);
    }


}
