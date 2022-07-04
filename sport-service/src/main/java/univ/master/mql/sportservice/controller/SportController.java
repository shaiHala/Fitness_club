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
import univ.master.mql.sportservice.entities.Individual;
import univ.master.mql.sportservice.entities.Sport;
import univ.master.mql.sportservice.services.SportService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sports/sport/")
//@Api(description = "Sport API having endpoints which are used to interact with sport micrservice")
public class SportController {
    @Autowired
    private SportService service;

	@PostMapping("/addIndividual")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Add sport", description = "Add new individual sport informations")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation",
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = Individual.class)))) })
	public ResponseEntity addSport( @Parameter(description="Individual sport object") @RequestBody Individual sport) {
		service.addIndividualSport(sport);
		return new ResponseEntity("Sport add succesfully", HttpStatus.OK);
	}
	@PostMapping("/addCollectif")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Add sport", description = "Add new collectif sport informations")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation",
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = Collectif.class)))) })
	public ResponseEntity addCollectifSport( @Parameter(description="Collectif sport object") @RequestBody Collectif sport) {
		service.addCollectiveSprot(sport);
		return new ResponseEntity("Sport add succesfully", HttpStatus.OK);
	}
    @Operation(summary =  "View a list of sports")
	@ApiResponses (value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved list",content = @Content(array = @ArraySchema(schema = @Schema(implementation = Sport.class)))),
			@ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
			@ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
	}
	)
	@GetMapping("/")
	public List<Sport> getSports() {
		return service.getSports();
	}
    @Operation(summary =   "Search a sport with an ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation",
					content = @Content(schema = @Schema(implementation = Sport.class))) })

	@GetMapping("/sport")
	public Optional<Sport> getSport(@RequestParam Long id) {
		return service.getSport(id);
	}
	@Operation(summary =   "Is this sport need trainer")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation",
					content = @Content(schema = @Schema(implementation = boolean.class))) })
	@GetMapping("/withtrainer")
	public boolean withTrainer(@RequestParam Long id) {
		return service.withTrainer(id);
	}

    @Operation(summary =   "Delete a sport by ID")
	@GetMapping("/remove")
	public ResponseEntity removeSportById(@RequestParam Long id) {
		System.err.println("remove sport with id :: "+id);
		service.removeSportById(id);
		return new ResponseEntity("Sport has been removed succefully", HttpStatus.OK);

	}


}
