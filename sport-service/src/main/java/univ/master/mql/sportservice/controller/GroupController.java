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
import univ.master.mql.sportservice.entities.Collectif;
import univ.master.mql.sportservice.entities.Sport;
import univ.master.mql.sportservice.entities.Group;
import univ.master.mql.sportservice.entities.Trainer;
import univ.master.mql.sportservice.services.GroupService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sports/groups/")

//@Api(description = "Group API having endpoints which are used to interact with group micrservice")
public class GroupController {
    @Autowired
    private GroupService service;


	@PostMapping("/add")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Add group", description = "Add new group informations")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation",
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = Group.class)))) })
	public ResponseEntity addGroup( @Parameter(description="Group object") @RequestBody Group group) {
		service.addGroup(group);
		return new ResponseEntity("Group add succesfully", HttpStatus.OK);
	}
    @Operation(summary =  "View a list of groups")
	@ApiResponses (value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved list",content = @Content(array = @ArraySchema(schema = @Schema(implementation = Group.class)))),
			@ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
			@ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
	}
	)
	@GetMapping("/")
	public List<Group> getGroups() {
		return service.getGroups();
	}
    @Operation(summary =   "Search a group with an ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation",
					content = @Content(schema = @Schema(implementation = Group.class))) })

	@GetMapping("/group")
	public Optional<Group> getGroup(@RequestParam Long id) {
		return service.getGroup(id);
	}
    @Operation(summary =   "Delete a group by ID")
	@GetMapping("/remove")
	public ResponseEntity removeGroupById(@RequestParam Long id) {
		System.err.println("remove group with id :: "+id);
		service.removeGroupById(id);
		return new ResponseEntity("Group has been removed succefully", HttpStatus.OK);

	}
	@Operation(summary =   "Delete a group by sport")
	@GetMapping("/removeBySport")
	public ResponseEntity removeGroupBySport(@RequestParam Long id) {
		Sport sport=new Sport();
		sport.setId(id);
		service.removeGroupBySport(sport);
		return new ResponseEntity("Group has been removed succefully", HttpStatus.OK);

	}


	@Operation(summary =  "Set trainer of the group")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation" , content = @Content(schema = @Schema(implementation = Group.class))) })
	@PutMapping("/changeTrainer")
	public Optional<Group> setTrainer(@RequestBody  Trainer trainer,@RequestParam Long id){
		return service.setTrainer(trainer,id);
	}


	@Operation(summary =  "Set group's sport")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation" , content = @Content(schema = @Schema(implementation = Group.class))) })
	@GetMapping("/changeSport")
	public Optional<Group> setSport(@RequestParam  Long sport,@RequestParam  Long id){
		Collectif s=new Collectif();
		s.setId(sport);
		System.err.println(s.getId());
		return service.setSport(s,id);
	}

}
