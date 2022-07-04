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
import univ.master.mql.subscriptionservice.entities.Offer;
import univ.master.mql.subscriptionservice.entities.Pack;
import univ.master.mql.subscriptionservice.services.OfferService;

import java.util.List;
import java.util.Optional;

@RestController

@Slf4j
@RequestMapping("/api/subscription/offer/")
public class OfferController {
    @Autowired
    private OfferService service;

    @Operation(summary =   "Search a package with an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = Offer.class))) })

    @GetMapping(path="/byPackage")
    public List<Offer> getByPackage(@RequestParam int id){
        Pack p=new Pack();
        p.setId(Long.parseLong(""+id));
        return service.getOffersByPackage(p);
    }

    @Operation(summary =  "View a list of offers")
    @ApiResponses (value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",content = @Content(array = @ArraySchema(schema = @Schema(implementation = Offer.class)))),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
            @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping(path="/all")
    public List<Offer> getAll(){
        return service.getOffers();
    }


    @Operation(summary =   "Search a offer with an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = Offer.class))) })

    @GetMapping("/")
    public Optional<Offer>  getById(@RequestParam Long id){
        return service.getOfferById(id);
    }



    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add offer", description = "Add new Offer informations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Offer.class)))) })
    @PostMapping(path="add", consumes= MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity add(@RequestBody Offer offer){
        System.err.println(offer.toString());
        service.add(offer);
            return new ResponseEntity("Offer add succesfully", HttpStatus.OK);

    }


    @Operation(summary =   "Update a Offer information")
    @PutMapping("/edit")
    public ResponseEntity edit(@RequestBody Offer offer){
        service.editOffer(offer);
        return new ResponseEntity("Offer updated successfully", HttpStatus.OK);
    }


    @Operation(summary =   "Delete a offer by package")
    @GetMapping("/removeByPack")
    public ResponseEntity removeByPack(@RequestParam Long id){
        Pack c= new Pack();
        c.setId(id);
        service.removeOfferByPack(c);
        return new ResponseEntity("Offer has been removed succefully", HttpStatus.OK);

    }

    @Operation(summary =   "Delete a offer by ID")
    @GetMapping("/remove")
    public void remove(@RequestParam Long id){
        service.removeOffer(id);
    }

}
