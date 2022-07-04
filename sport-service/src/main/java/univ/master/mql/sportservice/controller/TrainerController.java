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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import univ.master.mql.sportservice.entities.Collectif;
import univ.master.mql.sportservice.entities.Sport;
import univ.master.mql.sportservice.entities.Trainer;
import univ.master.mql.sportservice.repository.TrainerRepository;
import univ.master.mql.sportservice.services.TrainerService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sports/trainers/")

//@Api(description = "Client API having endpoints which are used to interact with client micrservice")
public class TrainerController {
    @Autowired
    private TrainerService service;


	@PostMapping(path="add", consumes= MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Add trainer", description = "Add new trainer informations")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation",
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = Trainer.class)))) })
	public ResponseEntity addTrainer( @Parameter(description="Trainer object") @RequestBody Trainer trainer) {
		service.addTrainer(trainer);
		return new ResponseEntity("Trainer add succesfully", HttpStatus.OK);
	}

    @Operation(summary =  "View a list of trainers")
	@ApiResponses (value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved list",content = @Content(array = @ArraySchema(schema = @Schema(implementation = Trainer.class)))),
			@ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
			@ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
	}
	)
	@GetMapping("/")
	public List<Trainer> getTrainers() {
		return service.getTrainers();
	}
    @Operation(summary =   "Search a trainer with an ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation",
					content = @Content(schema = @Schema(implementation = Trainer.class))) })

	@GetMapping("/trainer")
	public Optional<Trainer> getTrainer(@RequestParam Long id) {
		return service.getTrainer(id);
	}
    @Operation(summary =   "Delete a trainer by ID")
	@GetMapping("/removeId")
	public ResponseEntity removeTrainerById(@RequestParam Long id) {
		System.err.println("remove client with id :: "+id);
		service.removeTrainerById(id);
		return new ResponseEntity("Client has been removed succefully", HttpStatus.OK);

		}
		@Operation(summary =   "Update a trainer information")
	@PutMapping("/edit")
	public ResponseEntity editTrainer(@RequestBody Trainer trainer){
		Trainer t = service.editTrainer(trainer);
		return new ResponseEntity("Product updated successfully", HttpStatus.OK);
	}
	@Operation(summary =  "Check the availability of trainer with ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Trainer.class)))) })

	@GetMapping("/isAvailible")
	public List<Trainer> getAvailibleTrainers(){
        return service.getAvailibleTrainers();
	}
	@Operation(summary =  "Change the availability of trainer with ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation" ,content = @Content(schema = @Schema(implementation = Trainer.class))) })
	@GetMapping("/setAvailiblity")
	public Optional<Trainer> setTrainerAvailability(@RequestParam Long id ,@RequestParam Boolean availible){
		return service.setTrainerAvailability(id,availible);
	}

	@Operation(summary =  "Get the unvailabile trainers")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation",content = @Content(array = @ArraySchema(schema = @Schema(implementation = Trainer.class)))) })
	@GetMapping("/getUnvailible")
	public List<Trainer> getUnvailibleTrainers(){
		return service.getUnvailibleTrainers();
	}
	@Operation(summary =  "Get the unvailabile trainers")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation" , content = @Content(schema = @Schema(implementation = Trainer.class))) })
	@GetMapping("/changeSport")
	public Optional<Trainer> changeSport(@RequestParam Long sport_id,@RequestParam  Long id){
	    Collectif s=new Collectif();
		s.setId(sport_id);
		System.err.println(s.getId());
		return service.changeSport(s,id);
	}
}
