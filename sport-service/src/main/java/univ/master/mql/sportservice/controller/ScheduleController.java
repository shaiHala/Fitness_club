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
import univ.master.mql.sportservice.entities.Group;
import univ.master.mql.sportservice.entities.Schedule;
import univ.master.mql.sportservice.entities.Sport;
import univ.master.mql.sportservice.entities.Trainer;
import univ.master.mql.sportservice.services.ScheduleService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sports/schedule/")

//@Api(description = "Schedule API having endpoints which are used to interact with schedule micrservice")
public class ScheduleController {
    @Autowired
    private ScheduleService service;


	@PostMapping("/add")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Add schedule", description = "Add new schedule informations")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation",
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = Schedule.class)))) })
	public ResponseEntity addSchedule( @Parameter(description="Schedule object") @RequestBody Schedule schedule) {
		service.addSchedule(schedule);
		return new ResponseEntity("Schedule add succesfully", HttpStatus.OK);
	}
    @Operation(summary =  "View a list of schedules")
	@ApiResponses (value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved list",content = @Content(array = @ArraySchema(schema = @Schema(implementation = Schedule.class)))),
			@ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
			@ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
	}
	)
	@GetMapping("/")
	public List<Schedule> getSchedules() {
		return service.getSchedules();
	}
    @Operation(summary =   "Search a schedule with an ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation",
					content = @Content(schema = @Schema(implementation = Schedule.class))) })

	@GetMapping("/schedule")
	public Optional<Schedule> getSchedule(@RequestParam Long id) {
		return service.getSchedule(id);
	}
    @Operation(summary =   "Delete a schedule by ID")
	@GetMapping("/remove")
	public ResponseEntity removeScheduleById(@RequestParam Long id) {
		System.err.println("remove schedule with id :: "+id);
		service.removeScheduleById(id);
		return new ResponseEntity("Schedule has been removed succefully", HttpStatus.OK);

	}


	@Operation(summary =  "Set group in schedule")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation" ,
                    content = @Content(schema = @Schema(implementation = Schedule.class))) })
	@PutMapping("/changeGroup")
	public Optional<Schedule> setGroupe(@RequestBody Group group, Long id){
		return service.setGroup(group,id);
	}


    @Operation(summary =  "Enable schedule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation" , content = @Content(schema = @Schema(implementation = Schedule.class))) })
    @GetMapping("/enable")
    public Optional<Schedule> enable(Long id){
        return service.enable(id);
    }


    @Operation(summary =  "Disable schedule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation" , content = @Content(schema = @Schema(implementation = Schedule.class))) })
    @GetMapping("/disable")
    public Optional<Schedule> disable(Long id){
        return service.disable(id);
    }
    @Operation(summary =   "Update a Schedule information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation" ) })
    @PutMapping("/edit")
    public ResponseEntity editSchedule(@RequestBody  Schedule schedule){
        Schedule t = service.editSchedule(schedule);
        return new ResponseEntity("Product updated successfully", HttpStatus.OK);
    }



}
