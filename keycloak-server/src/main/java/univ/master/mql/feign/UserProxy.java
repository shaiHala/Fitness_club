package univ.master.mql.feign;

import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import univ.master.mql.DTO.UserDTO;

@FeignClient(name="administrative-service", url="localhost:8090", configuration = FeignAutoConfiguration.class)
public interface UserProxy {

    @PostMapping(value ="/api/users/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity add(@RequestBody UserDTO user) ;

    @GetMapping(value="/api/users/test")
    public  String tst();

}
