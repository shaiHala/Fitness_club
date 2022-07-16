package univ.master.mql.services;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import univ.master.mql.DTO.UserDTO;
import univ.master.mql.feign.UserProxy;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private String authServerUrl = "http://localhost:8080/auth/";
    private String realm = "fitness-club-app";
    private String clientId = "user-ms";
    private String role = "user";
    //Get client secret from the Keycloak admin console (in the credential tab)
    private String clientSecret = "lsmFBHqM2GrHkr9CBASVlxVXYPcif4PJ";
    private final Map<String, List<String>> roleMapping = new HashMap<>();

    private UserProxy proxy;

    public UserService(UserProxy proxy) {
        this.proxy = proxy;
    }

    public String test(){
        return proxy.tst();
    }
    public UserRepresentation create(UserDTO appUser) {

        List<String> list=new ArrayList<>();
        list.add("user");
        if (appUser instanceof ClientResource) {
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

        Map<String, List<String> > otherAttribute = new HashMap<String, List<String>>();
        otherAttribute.put("phoneNumber", Collections.singletonList(appUser.getPhone()));
        otherAttribute.put("gender",Collections.singletonList(appUser.getGender()));
        otherAttribute.put("birthdate",Collections.singletonList(appUser.getBirthdate()));
        otherAttribute.put("cin",Collections.singletonList(appUser.getCin()));
        otherAttribute.put("address",Collections.singletonList(appUser.getAddress()));
 System.err.println(otherAttribute);
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(appUser.getUsername());
        userRepresentation.setEmail(appUser.getEmail());
        userRepresentation.setLastName(appUser.getLastname());
        userRepresentation.setFirstName(appUser.getFirstname());
        userRepresentation.setAttributes(otherAttribute);
        userRepresentation.setClientRoles(roleMapping);
        userRepresentation.setCredentials(Collections.singletonList(cr));
        userRepresentation.setEnabled(true);
        System.err.println(userRepresentation.getAttributes().get("cin"));
//        userRepresentation.getRequiredActions().add("UPDATE_PROFILE");
//        userRepresentation.getRequiredActions().add("UPDATE_PASSWORD");
        List <String > action=new ArrayList<>();
        action.add("UPDATE_PROFILE");
        action.add("UPDATE_PASSWORD");
        userRepresentation.setRequiredActions(action);
        keycloak.realm(realm).users().create(userRepresentation);

        List<UserRepresentation> userList = keycloak.realm(realm).users().search(appUser.getUsername()).stream()
                .filter(userRep -> userRep.getUsername().equals(appUser.getUsername())).collect(Collectors.toList());
        userRepresentation = userList.get(0);

        System.err.println("User with id: " + userRepresentation.getId() + " created");

        this.assignRoleToUser(userRepresentation.getId(), list.get(0));
        appUser.setId(userRepresentation.getId());
        proxy.add(appUser);

        return  userRepresentation;
    }


    public AccessTokenResponse signin( UserDTO userDTO) {

        Map<String, Object> clientCredentials = new HashMap<>();
        clientCredentials.put("secret", clientSecret);
        clientCredentials.put("grant_type", "password");

        Configuration configuration =
                new Configuration(authServerUrl, realm, clientId, clientCredentials, null);
        AuthzClient authzClient = AuthzClient.create(configuration);



        AccessTokenResponse response =
                authzClient.obtainAccessToken(userDTO.getEmail(), userDTO.getPassword());

        return response;
    }



    private void assignRoleToUser(String userId, String role) {
//        Keycloak keycloak = KeycloakBuilder.builder().serverUrl(authServerUrl)
//                .grantType(OAuth2Constants.PASSWORD).realm("master").clientId("admin-cli")
//                .username("admin").password("admin")
//                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build()).build();
//        keycloak.tokenManager().getAccessToken();
        Keycloak keycloak = getAdminKeycloak();
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

    private Keycloak getAdminKeycloak() {
        Keycloak keycloak = KeycloakBuilder.builder().serverUrl(authServerUrl)
                .grantType(OAuth2Constants.PASSWORD).realm("master").clientId("admin-cli")
                .username("admin").password("admin")
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build()).build();
        keycloak.tokenManager().getAccessToken();
        return keycloak;
    }



    public List< UserRepresentation > getUsers() {
        Keycloak keycloak=getAdminKeycloak();
        List<UserRepresentation> userList = keycloak.realm(realm).users().list();
        System.err.println(userList.stream().count());
        List<UserRepresentation> list= new ArrayList<>();
        for (UserRepresentation user : userList) {
            UserResource userResource = keycloak.realm(realm).users().get(user.getId());
            ClientRepresentation clientRepresentation = keycloak.realm(realm).clients().findByClientId(clientId).get(0);
            List<RoleRepresentation> roles=  userResource.roles().clientLevel(clientRepresentation.getId()).listAll();
            RoleRepresentation r=  roles. stream() .filter(role -> role.getName().equals("user"))
                    .findAny()
                    .orElse(null);
            if(r!=null){
                list.add(user);
                System.err.println("delete :: "+userList.stream().count()+" ==> user name "+user.getUsername());
            }


        }
        System.err.println("loool :: "+userList.stream().count());
//        return  keycloak.realm(realm).users().list();
        return list;
    }

    public boolean deleteKeycloakUser(String username) {

        Keycloak keycloak = getAdminKeycloak();
        List<UserRepresentation> userList = keycloak.realm(realm).users().search(username);

        UserResource userResource = keycloak.realm(realm).users().get(userList.get(0).getId());
        ClientRepresentation clientRepresentation = keycloak.realm(realm).clients().findByClientId(clientId).get(0);
        List<RoleRepresentation> roles=  userResource.roles().clientLevel(clientRepresentation.getId()).listAll();
        RoleRepresentation r=  roles. stream() .filter(role -> role.getName().equals("user"))
                .findAny()
                .orElse(null);

        System.err.println(r);
        if(r==null){
            for (RoleRepresentation role : roles) {

                if (role.getName().equals("coach") || role.getName().equals("member") ) {
                    keycloak.realm(realm).users().delete(userList.get(0).getId());
                    return true;
                }
                else {
                    System.err.println(role);
                }
            }

        }

        return false;
    }

    private Keycloak getUserMsKeycloak() {
        Keycloak keycloak = KeycloakBuilder.builder().serverUrl(authServerUrl)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS).realm(realm).clientId(clientId)
                .clientSecret(clientSecret)
//                .password("admin").username("admin")
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build()).build();
        keycloak.tokenManager().getAccessToken();
        return keycloak;
    }

    public boolean updateUser (UserDTO userDTO,String  username){
         Keycloak keycloak=getAdminKeycloak();
        UsersResource usersResource = keycloak.realm(realm).users();
        Optional<UserRepresentation> userList = usersResource.list().stream().filter(user -> user.getUsername().equals(username)).findAny();

        if (userList.isPresent()) {
            UserRepresentation usr = userList.get();

            if(usr.getRequiredActions().contains("UPDATE_PROFILE")){
                usr.setRequiredActions(new ArrayList());
                if(userDTO.getFirstname()!="") usr.setFirstName(userDTO.getFirstname());
                if(userDTO.getLastname()!="") usr.setLastName(userDTO.getLastname());
                if(userDTO.getEmail()!="") usr.setEmail(userDTO.getEmail());
                if(userDTO.getUsername()!="") usr.setUsername(userDTO.getUsername());
                if(userDTO.getPhone()!=null) usr.getAttributes().get("phone").set(0,userDTO.getPhone());
                if(userDTO.getGender()!=null) usr.getAttributes().get("gender").set(0,userDTO.getGender());
                if(userDTO.getBirthdate()!="") usr.getAttributes().get("birthdate").set(0,userDTO.getBirthdate());
                if(userDTO.getCin()!="")  usr.getAttributes().get("cin").set(0,userDTO.getCin());
                if(userDTO.getAddress()!="") usr.getAttributes().get("address").set(0,userDTO.getAddress());

                UserResource userResource = usersResource.get(usr.getId());

                usr.getRequiredActions().add("UPDATE_PROFILE");
                usr.getRequiredActions().add("UPDATE_PASSWORD");
                userResource.update(usr);
                return true;
            }
            return false;
        }
        return false;
    }

}
