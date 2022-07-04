package univ.master.mql.subscriptionservice.feign;

import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import univ.master.mql.subscriptionservice.dto.SportDTO;
import univ.master.mql.subscriptionservice.dto.SportPackageDTO;

import java.util.Optional;

@FeignClient(name="sport-service", url="localhost:8092", configuration = FeignAutoConfiguration.class)
public interface SportPackageProxy {
    @PostMapping(value ="/api/sports/sportPackages/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity addSportPackage(@RequestBody SportPackageDTO sp) ;

    @GetMapping(value ="/api/sports/sportPackages/remove", consumes = "application/json", produces = "application/json")
    public ResponseEntity removeSportPackageyId(@RequestParam Long id);

    @GetMapping(value ="/api/sports/sportPackages/removeBySport", consumes = "application/json", produces = "application/json")
    public ResponseEntity removeBySport(@RequestParam Long id) ;

    @GetMapping(value ="/api/sports/sportPackages/removeByPackage", consumes = "application/json", produces = "application/json")
    public ResponseEntity removeByPackage(@RequestParam Long id);

    @PutMapping(value ="/api/sports/sportPackages/changePackage", consumes = "application/json", produces = "application/json")
    public Optional<SportPackageDTO> setPackage(@RequestParam Long id_package, @RequestParam Long id);


    @PutMapping(value ="/api/sports/sportPackages/changeSport", consumes = "application/json", produces = "application/json")
    public Optional<SportPackageDTO> setSport(@RequestBody SportDTO sport, @RequestParam Long id);

    @PutMapping(value ="/api/sports/sportPackages/changeCourses", consumes = "application/json", produces = "application/json")
    public Optional<SportPackageDTO> setNbCourses(@RequestParam int nb,@RequestParam Long id);


    }
