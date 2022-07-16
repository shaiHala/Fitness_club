package univ.master.mql.administrationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import univ.master.mql.administrationservice.DTO.UserDTO;
import univ.master.mql.administrationservice.entity.User;
import univ.master.mql.administrationservice.services.UserService;

@RequestMapping("/api/users/")
@RestController
public class UserController {
  @Autowired
    public UserService service;


  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping(value="/add", consumes = "application/json", produces = "application/json")
//  @Operation(summary = "Add sport_package  record", description = "Add  new many to many relation")
//  @ApiResponses(value = {
//          @ApiResponse(responseCode = "200", description = "successful operation",
//                  content = @Content(array = @ArraySchema(schema = @Schema(implementation = Group.class)))) })

  public ResponseEntity add (@RequestBody UserDTO userdto) {
      System.err.println(userdto.toString());
      User user = User.builder()
              .id(userdto.getId())
              .username(userdto.getUsername())
              .firstname(userdto.getFirstname())
              .lastname(userdto.getLastname())
              .cin(userdto.getCin())
              .phone(userdto.getPhone())
              .address(userdto.getAddress())
              .gender(userdto.getGender())
              .birthdate(userdto.getBirthdate())
              .email(userdto.getEmail())
              .password(userdto.getPassword())
              .build();

        service.addUser(user);

      return new ResponseEntity(" User record add succesfully", HttpStatus.OK);
    }

    @PutMapping("/update")
    public User update(@RequestBody  User user) {
       return service.editUser(user);
    }

    @GetMapping("/deleteByUsername")
    public void deleteByUsername(@RequestParam  String username) {
        service.removeUserByusername(username);
    }

    @GetMapping("/deleteById")
    public void deleteById(@RequestParam  String id) {
        service.removeUserById(id);
    }

    @GetMapping("/getUser")
    public void getUserById(@RequestParam  String id) {
        service.getUser(id);
    }
    @GetMapping("/getUserByUsername")
    public void getUserByUsername(@RequestParam  String username) {
        service.getUserByUsername(username);
    }



    @GetMapping("/test")
    public String tst() {
        return "I test it !!!";
    }



}
