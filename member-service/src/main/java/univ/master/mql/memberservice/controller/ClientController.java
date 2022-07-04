package univ.master.mql.memberservice.controller;

import java.util.*;

//import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import univ.master.mql.memberservice.dto.ClientRequest;
import univ.master.mql.memberservice.dto.ClientResponse;
import univ.master.mql.memberservice.entities.Client;
import univ.master.mql.memberservice.keycloakservice.KeycloackService;
import univ.master.mql.memberservice.services.ClientService;

@RestController
@RequestMapping("/api/member/clients")

//@Api(description = "Client API having endpoints which are used to interact with client micrservice")
public class ClientController {

	@Autowired
	private KeycloackService keyloack;
    @Autowired
    private ClientService clientService;

//	@GetMapping(path = "/{userName}")
//	public List<UserRepresentation> getUser(@PathVariable("userName") String userName){
//		List<UserRepresentation> user = clientService.getUser(userName);
//		return user;
//	}

	@GetMapping("/auth")
	public String getAdminKeycloak() {
		Keycloak key= keyloack.getAdminKeycloak();
		return "hELLO";
	}


	@PostMapping(path = "/signin")
	public ResponseEntity<?> signin(@RequestBody  ClientRequest userDTO) {
		return keyloack.signin(userDTO);
	}


	@GetMapping(path = "/test")
	public String getUser(){
//		List<UserRepresentation> user = clientService.getUser(userName);
		return "halaaaaa";
	}

	@PostMapping(path = "/create")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "create client", description = "Add new create informations")
	public ResponseEntity<?> createUser(@RequestBody ClientRequest userDTO) {
		System.err.println("hello ");
	       userDTO.setPassword(keyloack.create(userDTO).getCredentials().get(0).getCredentialData());
		   clientService.addClient(userDTO);
			System.err.println("cr ::  "+userDTO.getPassword());

		return ResponseEntity.ok(userDTO);
	}

	@PostMapping("/add")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Add client", description = "Add new client informations")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation",
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = Client.class)))) })

	public ResponseEntity addClient( @Parameter(description="Client object") @RequestBody 	ClientRequest clientRequest) {
		System.err.println(clientRequest.toString());
		clientService.addClient(clientRequest);
		return new ResponseEntity("Client add succesfully", HttpStatus.OK);
	}


    @Operation(summary =  "View a list of client")
	@ApiResponses (value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
			@ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
	}
	)
	@GetMapping("/")
	public List<ClientResponse> getClients() {
		return clientService.getClients();
	}


@Operation(summary =   "Search a client with an ID")
	@GetMapping("/client")
	public Optional<ClientResponse> getClient(@RequestParam Long id) {
		return clientService.getClient(id);
	}

@Operation(summary =   "Delete a client by membership")
	@GetMapping("/remove")
	public ResponseEntity  removeClientByMembership(@RequestParam String memberShip) {
		System.err.println("remove client with membership :: "+memberShip);
		clientService.removeClient(memberShip);
		return new ResponseEntity("Client with memberShip number  :: "+memberShip+"has been removed succefully" , HttpStatus.OK);



	}

@Operation(summary =   "Delete a client by ID")
	@GetMapping("/removeId")
	public ResponseEntity removeClientById(@RequestParam Long id) {
		System.err.println("remove client with id :: "+id);
		clientService.removeClientById(id);
		return new ResponseEntity("Client has been removed succefully", HttpStatus.OK);

	}
@Operation(summary =   "Update a client")
	@PutMapping("/edit")
	public ResponseEntity editClientResponse( Client client){
		Client c = clientService.editClient(client);
//			return ClientResponse.builder()
//					.cin(c.getCin())
//					.fName(c.getFName())
//					.lName(c.getLName())
//					.status(c.getStatus())
//					.membership(c.getMembershipnum())
//					.inscriptionDate(c.getInscriptionDate())
//					.dateOfBirth(c.getDateOfBirth())
//					.build();

		return new ResponseEntity("Product updated successfully", HttpStatus.OK);
	}

	@Operation(summary =  "Check id the client account is active")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation",
					content = @Content(schema = @Schema(implementation = Boolean.class))) })

	@GetMapping("/isActive")
	public boolean isActive(String memberShip){
        return clientService.isActive(memberShip);
	}

}
