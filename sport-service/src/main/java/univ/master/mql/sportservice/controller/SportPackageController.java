package univ.master.mql.sportservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import univ.master.mql.sportservice.dto.SportDTO;
import univ.master.mql.sportservice.dto.SportPackageDTO;
import univ.master.mql.sportservice.entities.*;
import univ.master.mql.sportservice.services.GroupService;
import univ.master.mql.sportservice.services.SportPackageService;
import univ.master.mql.sportservice.services.SportService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sports/sportPackages/")

//@Api(description = "Group API having endpoints which are used to interact with group micrservice")
public class SportPackageController {
    @Autowired
    private SportPackageService service;

	@Autowired
	private SportService sportService;

	@PostMapping(value="/add", consumes = "application/json", produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Add sport_package  record", description = "Add  new many to many relation")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation",
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = Group.class)))) })
	public ResponseEntity addSportPackage( @Parameter(description="Group object") @RequestBody SportPackageDTO sp) {
	    Sport s= new Sport();
		s.setId(sp.getSport().getId());
		SportPackage sportPack=new SportPackage( sp.getId(),sp.getSport(), sp.getId_pack(),sp.getNb_courses());
		System.err.println(sportPack);
		System.err.println(sp);
		service.addSportPackage(sportPack);
		return new ResponseEntity(" Sport_Package record add succesfully", HttpStatus.OK);
	}


    @Operation(summary =   "Delete a relation sport package by ID")
	@GetMapping("/remove")
	public ResponseEntity removeSportPackageyId(@RequestParam Long id) {
		System.err.println("remove group with id :: "+id);
		service.removeGroupById(id);
		return new ResponseEntity("Sport_Package has been removed succefully", HttpStatus.OK);
	}
	@Operation(summary =   "Delete a group by sport")
	@GetMapping("/removeBySport")
	public ResponseEntity removeBySport(@RequestParam Long id) {
		Sport sport=new Sport();
		sport.setId(id);
		service.removeAllBySport(sport);
		return new ResponseEntity("Sport_Package has been removed succefully", HttpStatus.OK);

	}

	@Operation(summary =   "Delete a group by package")
	@GetMapping("/removeByPackage")
	public ResponseEntity removeByPackage(@RequestParam Long id) {
		service.removeByPackage(id);
		return new ResponseEntity("Sport_Package has been removed succefully", HttpStatus.OK);

	}


	@Operation(summary =  "Set sport of the Sport_Package relation")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation" , content = @Content(schema = @Schema(implementation = SportPackage.class))) })
	@PutMapping("/changeSport")
	public Optional<SportPackage> setSport(@RequestBody SportDTO sport, @RequestParam Long id){
		System.err.println(sport);
		Sport s1=sportService.getSport(sport.getId()).get();
		System.err.println(s1);
		return service.setSport(s1,id);
	}

	@Operation(summary =  "Set sport of the Sport_Package relation")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation" , content = @Content(schema = @Schema(implementation = SportPackage.class))) })
	@PutMapping("/changePackage")
	public Optional<SportPackage> setPackage(@RequestParam Long id_package,@RequestParam Long id){
		return service.setPackage(id,id_package);
	}

    @Operation(summary =  "Set number of courses of the Sport_Package relation")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation" , content = @Content(schema = @Schema(implementation = SportPackage.class))) })
	@PutMapping("/changeCourses")
	public Optional<SportPackage> setNbCourses(@RequestParam int nb,@RequestParam Long id){
		return service.setNbCourse(id,nb);
	}



}
