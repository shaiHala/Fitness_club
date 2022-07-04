package univ.master.mql.memberservice.keycloakservice;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.admin.client.token.TokenManager;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.util.TokenUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import univ.master.mql.memberservice.dto.ClientRequest;

import java.util.*;
import java.util.stream.Collectors;

@RequestMapping("/users")
@RestController
public class KeycloackService {

    private String authServerUrl = "http://localhost:8081/";
    private String realm = "fitness-club";
    private String clientId = "fitness-club-service";
    private String role = "member";
    private String clientSecret = "vAujqG1Adpr8AFQpBiYSraZ1OQvtFzj2";
    private final Map<String, List<String>> roleMapping = new HashMap<>();

    public Keycloak getAdminKeycloak() {

        Keycloak keycloak = KeycloakBuilder.builder().serverUrl("http://localhost:8081/")
                .grantType(OAuth2Constants.PASSWORD).realm("master").clientId("admin-cli")
                .username("admin").password("admin")
//                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
                .build();
        keycloak.tokenManager().getAccessToken();
        AccessTokenResponse accessToken = keycloak.tokenManager().getAccessToken();

        return keycloak;
    }



    public UserRepresentation create(ClientRequest appUser) {
System.err.println("offffffffffffff");
        List<String> list=new ArrayList<>();
        list.add(role);
        if (appUser instanceof ClientRequest) {
            this.roleMapping.put(this.clientId, list);
        }

        Keycloak keycloak = KeycloakBuilder.builder().serverUrl(authServerUrl)
                .grantType(OAuth2Constants.PASSWORD).realm("master").clientId("admin-cli")
                .username("admin").password("admin")
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build()).build();
        keycloak.tokenManager().getAccessToken();

        CredentialRepresentation cr = new CredentialRepresentation();
        cr.setType(OAuth2Constants.PASSWORD);
        cr.setValue(appUser.getPassword());

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(appUser.getUsername());
        userRepresentation.setFirstName(appUser.getFName());
        userRepresentation.setLastName(appUser.getLName());
        userRepresentation.setEmail(appUser.getEmail());
System.err.println(cr);
        userRepresentation.setClientRoles(roleMapping);
        userRepresentation.setCredentials(Collections.singletonList(cr));
        userRepresentation.setEnabled(true);

        keycloak.realm(realm).users().create(userRepresentation);
        List<UserRepresentation> userList = keycloak.realm(realm).users().search(appUser.getUsername()).stream()
                .filter(userRep -> userRep.getUsername().equals(appUser.getUsername())).collect(Collectors.toList());
        userRepresentation = userList.get(0);
        System.err.println("User with id: " + userRepresentation.getId() + " created");


        this.assignRoleToUser(userRepresentation.getId(), list.get(0));

        return userRepresentation;
    }


    @PostMapping(path = "/signin")
    public ResponseEntity<?> signin(@RequestBody  ClientRequest userDTO) {

        Map<String, Object> clientCredentials = new HashMap<>();
        clientCredentials.put("secret", clientSecret);
        clientCredentials.put("grant_type", "password");

        Configuration configuration =
                new Configuration(authServerUrl, realm, clientId, clientCredentials, null);
        AuthzClient authzClient = AuthzClient.create(configuration);

        AccessTokenResponse response =
                authzClient.obtainAccessToken(userDTO.getEmail(), userDTO.getPassword());

        return ResponseEntity.ok(response);
    }
//    @RequestMapping(value = "/ajouter", method = RequestMethod.POST
//            , consumes = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/add")
    public String createKeycloakUser(@RequestBody ClientRequest appUser) {
         List<String> list=new ArrayList<>();
        list.add("member");
        if (appUser instanceof ClientRequest) {
            this.roleMapping.put(this.clientId, list);
        }
 return null;

    }

    private void assignRoleToUser(String userId, String role) {
        Keycloak keycloak = KeycloakBuilder.builder().serverUrl(authServerUrl)
                .grantType(OAuth2Constants.PASSWORD).realm("master").clientId("admin-cli")
                .username("admin").password("admin")
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build()).build();
        keycloak.tokenManager().getAccessToken();

        UsersResource usersResource = keycloak.realm(realm).users();
        UserResource userResource = usersResource.get(userId);

        //getting client
        ClientRepresentation clientRepresentation = keycloak.realm(realm).clients().findAll().stream().filter(client -> client.getClientId().equals(clientId)).collect(Collectors.toList()).get(0);
        ClientResource clientResource = keycloak.realm(realm).clients().get(clientRepresentation.getId());
        //getting role
        RoleRepresentation roleRepresentation = clientResource.roles().list().stream().filter(element -> element.getName().equals(role)).collect(Collectors.toList()).get(0);
        //assigning to user
        userResource.roles().clientLevel(clientRepresentation.getId()).add(Collections.singletonList(roleRepresentation));

       System.err.println( clientRepresentation.getAccess());


    }





    @GetMapping(path = "/getUsers")
    public List< UserRepresentation > getUsers() {
        Keycloak keycloak = KeycloakBuilder.builder().serverUrl(authServerUrl)
                .grantType(OAuth2Constants.PASSWORD).realm("master").clientId("admin-cli")
                .username("admin").password("admin")
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build()).build();
        keycloak.tokenManager().getAccessToken();


//        RoleRepresentation clientRole = clientResource.roles().get(role).toRepresentation(); //<-- here
//        userResource.roles().clientLevel(clientId).add(Collections.singletonList(clientRole));



        return  keycloak.realm(realm).users().list();
    }

    @PostMapping(path = "/sup")
    public String deleteKeycloakUser(@RequestBody ClientRequest student) {
        Keycloak keycloak = getAdminKeycloak();
        List<UserRepresentation> userList = keycloak.realm(realm).users().search(student.getUsername());
        for (UserRepresentation user : userList) {
            if (user.getUsername().equals(student.getUsername())) {
                keycloak.realm(realm).users().delete(user.getId());
            }
        }
        return "hello";
    }




    @PreAuthorize("hasRole('user')")
    @GetMapping(path = "/forUser")
    public  String  isClient(){
        return "hello users";
    }
    @PreAuthorize("hasRole('member')"+"&&"+"hasRole('user')")
    @GetMapping(path = "/forClient")
    public  String  isUser(){
        return "hello client and user";
    }


}
