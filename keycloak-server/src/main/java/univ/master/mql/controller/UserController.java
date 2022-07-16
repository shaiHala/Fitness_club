package univ.master.mql.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.keycloak.representations.idm.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import univ.master.mql.DTO.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import univ.master.mql.services.UserService;

import java.util.*;

@RequestMapping("/api/keycloak/users")
@RestController
public class UserController {

    private String authServerUrl = "http://localhost:8080/auth/";
    private String realm = "fitness-club-app";
    private String clientId = "user-ms";
    private String role = "user";
    //Get client secret from the Keycloak admin console (in the credential tab)
    private String clientSecret = "hX4Rv6o7dkUFxAjbiflvJC1ABZMCoMnV";
    private final Map<String, List<String>> roleMapping = new HashMap<>();


    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public String test(){
        return userService.test();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add user", description = "Add new user informations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserDTO.class)))) })
    @PostMapping(value = "/create")
    public UserRepresentation create(@RequestBody UserDTO appUser) {
        return  userService.create(appUser);
    }


    @PostMapping(path = "/signin")
    public ResponseEntity<?> signin(@RequestBody  UserDTO userDTO) {
        return ResponseEntity.ok(userService.signin(userDTO));
    }

    @Operation(summary =  "View a list of user")
    @ApiResponses (value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
            @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
    }
    )
    @PreAuthorize("hasRole('user')")
    @GetMapping(path = "/getUsers")
    public List< UserRepresentation > getUsers() {
        return userService.getUsers();
    }

    @Operation(summary =   "Delete a user by username")
    @PreAuthorize("hasRole('user')")
    @GetMapping(path = "/delete")
    public boolean deleteKeycloakUser(@RequestParam String username) {
        return userService.deleteKeycloakUser(username);
    }



    @PreAuthorize("hasRole('user')")
    @GetMapping(path = "/forUser")
    public  String  isClient(){
        return "hello users";
    }


//    @PreAuthorize("hasRole('user')")

    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Update user", description = "update new user informations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserDTO.class)))) })

    @PutMapping(path = "/updateUser")
    public  boolean  update(@RequestBody UserDTO userDTO, @RequestParam  String username){
        return userService.updateUser(userDTO, username);
    }


}
