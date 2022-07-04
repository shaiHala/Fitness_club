package univ.master.mql.subscriptionservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import univ.master.mql.subscriptionservice.dto.SportDTO;
import univ.master.mql.subscriptionservice.entities.Offer;
import univ.master.mql.subscriptionservice.entities.Pack;
import univ.master.mql.subscriptionservice.entities.PackageSport;
import univ.master.mql.subscriptionservice.services.OfferService;
import univ.master.mql.subscriptionservice.services.PackageService;
import univ.master.mql.subscriptionservice.services.PackageSportService;

import java.util.List;
import java.util.Optional;

@RestController

@Slf4j
@RequestMapping("/api/subscription/pack/")
public class PackController {
    @Autowired
    private PackageService service;
    @Autowired
    private PackageSportService sv;


    @Operation(summary =   "Search a package with an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = Pack.class))) })

    @GetMapping(path="/byPackage")
    public Optional <Pack> getPackage(@RequestParam Long id){
        return service.getPack(id);
    }

    @Operation(summary =  "View a list of packages")
    @ApiResponses (value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",content = @Content(array = @ArraySchema(schema = @Schema(implementation = Pack.class)))),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
            @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping(path="/all")
    public List<Pack> getAll(){
        return service.getPacks();
    }


    @Operation(summary =   "Search a offer with an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = Offer.class))) })

    @GetMapping("/")
    public List<Pack> getById(@RequestParam String type){
        return service.getPackagesByType(type);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add new package", description = "Add new package informations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = Pack.class)))})
    @PostMapping(path="add", consumes= MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity add(@RequestBody Pack p,@RequestParam Long idSport,@RequestParam int nbCourses){
        Pack pack=service.add(p,idSport,nbCourses);
        return new ResponseEntity("Offer add succesfully", HttpStatus.OK);

    }


    @Operation(summary =   "Update a pack information")
    @PutMapping("/edit")
    public ResponseEntity edit(@RequestBody Pack pack){
        service.edit(pack);
        return new ResponseEntity("Offer updated successfully", HttpStatus.OK);
    }


    @Operation(summary =   "Delete a package by package type")
    @GetMapping("/removeByType")
    public ResponseEntity removeByPackageType(@RequestParam String type){
        service.removeAllByPackageType(type);
        return new ResponseEntity("Package has been removed succefully", HttpStatus.OK);
    }

    @Operation(summary =   "Delete a package by ID")
    @GetMapping("/remove")
    public ResponseEntity remove(@RequestParam Long id){
        service.disable(id);
        return new ResponseEntity("Package has been removed succefully", HttpStatus.OK);
    }

    @Operation(summary =   "Delete a package by ID")
    @GetMapping("/recoverPakckage")
    public ResponseEntity recover(@RequestParam Long id){
        service.enable(id);
        return new ResponseEntity("Package has been recovered succefully", HttpStatus.OK);
    }

    @Operation(summary =  "Set sport of the package_sport relation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation" , content = @Content(schema = @Schema(implementation = PackageSport.class))) })
    @GetMapping(value="/changeSport")
    public Optional<PackageSport> setSport(@RequestParam Long id_sport, @RequestParam Long id){
        System.err.println(id_sport+"======>"+id);
        return sv.setSport(id_sport,id) ;
    }

    @Operation(summary =  "Set sport of the Package_Sport relation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation" , content = @Content(schema = @Schema(implementation = PackageSport.class))) })
    @PostMapping(value="/changePackage", consumes= MediaType.APPLICATION_JSON_VALUE)
    public Optional<PackageSport> setPackage(@RequestBody Pack pack,@RequestParam Long id){
        return sv.setPackage(pack,id);
    }

    @Operation(summary =  "Set number of courses of the Package_Sport relation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation" ) })
    @GetMapping(value="/changeCourses")
    public Optional<PackageSport> setNbCourses(@RequestParam int nb,@RequestParam Long id){
        return sv.setNbCourse(id,nb);
    }


}
