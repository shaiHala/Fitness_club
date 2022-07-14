package univ.master.mql.administrationservice.controller;

import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.OAuth2Constants;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import univ.master.mql.administrationservice.DTO.UserDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;

@RequestMapping("/api/users")
@RestController
public class testController {

    private String authServerUrl = "http://localhost:8080/auth/";
    private String realm = "fitness-club-app";
    private String clientId = "user-ms";
    private String role = "member";
    //Get client secret from the Keycloak admin console (in the credential tab)
    private String clientSecret = "hX4Rv6o7dkUFxAjbiflvJC1ABZMCoMnV";
    private final Map<String, List<String>> roleMapping = new HashMap<>();
    static Keycloak keycloak = null;
    String keycloakUserListEndpoint;

    @GetMapping("/yes")
    public String yes(){
        return " hello dud";
    }


    @PostMapping(path = "/signin")
    public ResponseEntity<?> signin(@RequestBody  UserDTO userDTO) {

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

    @GetMapping(value="/userInfo",produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO test (Principal principal) {
        UserDTO user = new UserDTO();

        KeycloakAuthenticationToken kp = (KeycloakAuthenticationToken) principal;
        AccessToken token = kp.getAccount().getKeycloakSecurityContext().getToken();
        user.setId(token.getOtherClaims().get("user_id").toString());
        user.setUsername(token.getPreferredUsername());
        user.setFirstname(token.getGivenName());
        user.setDateOfBirth(token.getBirthdate());
        user.setEmail(token.getEmail());
        user.setGender(token.getGender());
        user.setPhone(token.getPhoneNumber());
        user.setLastname(token.getFamilyName());
        Map<String, Object> otherClaims = token.getOtherClaims();

        return user;
    }

    @GetMapping("/getUsers")
    public List<UserRepresentation> getUsers(HttpServletRequest request) {
            KeycloakSecurityContext context = (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());

            Keycloak keycloak = KeycloakBuilder.builder()
                    .serverUrl("http://localhost:8080/auth")
                    .realm(realm)
                    .authorization(context.getTokenString())
                    .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(20).build())
                    .build();

            List<UserRepresentation> list = keycloak.realm(realm).users().list();

            return list;
        }

    @GetMapping("/details")
    public UserDTO getUserDetails(Principal principal) {

        KeycloakAuthenticationToken kp = (KeycloakAuthenticationToken) principal;

        SimpleKeycloakAccount simpleKeycloakAccount = (SimpleKeycloakAccount) kp.getDetails();

        AccessToken token  = simpleKeycloakAccount.getKeycloakSecurityContext().getToken();

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.err.println(authentication.getDetails());


        return   UserDTO.builder().username(token.getName())
                .email(token.getEmail())
                .firstname(token.getName())
                .lastname(token.getFamilyName())
                .username(token.getPreferredUsername())
                .id(token.getId())
                .dateOfBirth(token.getPhoneNumber())
                .gender(token.getGender())
                .phone(token.getPhoneNumber())
                .build();
    }


    @PostMapping(value = "/unprotected-data")
    public String getName(@RequestBody UserDTO appUser) {

        System.err.println("pffee mered");
        System.err.println("hello");
        List<String> list=new ArrayList<>();
        list.add("user");
        if (appUser instanceof UserDTO) {
            this.roleMapping.put(this.clientId, list);
        }

//        Keycloak keycloak = KeycloakBuilder.builder().serverUrl("http://localhost:8081/")
//                .grantType(OAuth2Constants.PASSWORD).realm("master").clientId("admin-cli")
//                .username("admin").password("admin")
//                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build()).build();
        Keycloak keycloak = KeycloakBuilder.builder().serverUrl("http://localhost:8080/auth")
//                .grantType(OAuth2Constants.PASSWORD)
                .realm("master").clientId("admin-cli")
                .username("admin").password("admin")
                .clientSecret("hX4Rv6o7dkUFxAjbiflvJC1ABZMCoMnV")
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(20).build())
//                .resteasyClient(new ResteasyClientBuilder().defaultProxy("localhost", 8080, "http").build())
                .build();


//        Keycloak keycloak = initKeycloakWithAdminRole();
        keycloak.tokenManager().getAccessToken();

//        Keycloak adminKeycloak = getAdminKeycloak();
        CredentialRepresentation cr = new CredentialRepresentation();
        cr.setType(OAuth2Constants.PASSWORD);
        cr.setValue(appUser.getPassword());

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(appUser.getUsername());
        userRepresentation.setClientRoles(roleMapping);
        userRepresentation.setCredentials(Collections.singletonList(cr));
        userRepresentation.setEnabled(true);

        keycloak.realm(realm).users().create(userRepresentation);


//        List<UserRepresentation> userList = keycloak.realm(realm).users().search(appUser.getUsername()).stream()
//                .filter(userRep -> userRep.getUsername().equals(appUser.getUsername())).collect(Collectors.toList());
//        userRepresentation = userList.get(0);
        System.err.println("User with id: " + userRepresentation.getId() + " created");


//        this.assignRoleToUser(userRepresentation.getId(), list.get(0));

//         userRepresentation.toString();
        return "Hello, this api is not protected.";
    }
    @PostMapping("/add")
    public void addUser(@RequestBody  UserDTO userDTO){
        CredentialRepresentation credential = Credentials
                .createPasswordCredentials(userDTO.getPassword());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDTO.getUsername());
        user.setFirstName(userDTO.getFirstname());
        user.setLastName(userDTO.getLastname());
        user.setEmail(userDTO.getEmail());
        user.setCredentials(Collections.singletonList(credential));
        user.setEnabled(true);

        UsersResource instance = getThisInstance();
        instance.create(user);
    }
    public static Keycloak getInstance(){
        if(keycloak == null){

            keycloak = KeycloakBuilder.builder()
                    .serverUrl("http://localhost:8080/auth")
                    .realm("master")
                    .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                    .username("admin")
                    .password("admin")
                    .clientId("admin-cli")
                    .clientSecret("APAmwsds7Yg6QrX6NEoPXcj3yyniqEUQ")
                    .resteasyClient(new ResteasyClientBuilder()
                            .connectionPoolSize(20)
                            .build()
                                   )
                    .build();
        }
        return keycloak;
    }

    public UsersResource getThisInstance(){
        return getInstance().realm(realm).users();
    }




}
