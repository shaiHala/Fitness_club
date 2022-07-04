package univ.master.mql.memberservice.services;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import lombok.AllArgsConstructor;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import univ.master.mql.memberservice.dto.ClientRequest;
import univ.master.mql.memberservice.dto.ClientResponse;
import univ.master.mql.memberservice.entities.Client;
import univ.master.mql.memberservice.repository.ClientRepository;




import javax.transaction.Transactional;

@AllArgsConstructor
@Service
@Transactional
public class ClientService {
	  @Autowired
	  private ClientRepository clientRepo;


	@Value("${keycloak.realm}")
	private static String realm;
	  public void addClient(ClientRequest clientRequest) {

			Client client=Client.builder()
					.cin(clientRequest.getCin())
					.fName(clientRequest.getFName())
					.lName(clientRequest.getLName())
//					.status(clientRequest.getStatus())
					.inscriptionDate(clientRequest.getInscriptionDate())
					.dateOfBirth(clientRequest.getDateOfBirth())
					.gender(clientRequest.getGender())
					. address(clientRequest.getAddress())
					.email(clientRequest.getEmail())
					.phone(clientRequest.getPhone())
					.username(clientRequest.getUsername())
					.password(clientRequest.getPassword())
					.build();

	     	client.setMembershipnum(generateMemebership(client.getLName()));
	     	System.err.println(client.getMembershipnum());
	     	System.err.println(checkMembershipNum(client.getMembershipnum()));

	     	while(checkMembershipNum(client.getMembershipnum())==true) {

				System.out.println("MembershipNumber already exist");
		     	client.setMembershipnum(generateMemebership(client.getLName()));
		}

			System.out.println(client.toString());
			clientRepo.save(client);
	 }
	  public boolean checkMembershipNum(String number) {
		  System.out.println(clientRepo.findBymembershipnum(number));
		  if(clientRepo.findBymembershipnum(number)==null)
			  return false;
		  else
			  return true;
	  }
	  public List<ClientResponse> getClients(){
		 List<Client> clients= clientRepo.findAll();
		 return clients.stream().map(this::mapToClientResponse).toList();
	  }
	  public Optional<ClientResponse> getClient(Long id){
		  return Optional.ofNullable(mapToClientResponse(clientRepo.getById(id)));
	  }
	  private ClientResponse mapToClientResponse(Client client) {

		return ClientResponse.builder()
				.id(client.getId())
				.fName(client.getFName())
				.lName(client.getLName())
				.cin(client.getCin())
//				.status(client.getStatus())
				.dateOfBirth(client.getDateOfBirth())
				.inscriptionDate(client.getInscriptionDate())
				.membership(client.getMembershipnum())
				.gender(client.getGender())
				. address(client.getAddress())
				.email(client.getEmail())
		        .phone(client.getPhone())
		        .username(client.getUsername())
		        .password(client.getPassword())
				.build();
	}
	  public String generateMemebership(String lname) {
		  Random rand = new Random();
		  LocalDate current_date = LocalDate.now();
	      int year=current_date.getYear();
//	      System.out.println("test ::"+d.getYear());
	      String number="";
	      number=lname.charAt(0)+""+year+""+rand.nextInt(1000);
		return number;
	}
	  public int removeClientById(Long id){
		  return clientRepo.deleteAllById(id);
	  }
	  public Client editClient (Client client){
			return clientRepo.findById(client.getId())
					.map(clt -> {
						clt.setLName(client.getLName());
						clt.setFName(client.getFName());
						clt.setDateOfBirth(client.getDateOfBirth());
						clt.setInscriptionDate(client.getInscriptionDate());
						clt.setCin(client.getCin());
						clt.setGender(client.getGender());
						clt.setStatus(client.getStatus());
						clt.setAddress(client.getAddress());
						clt.setEmail(client.getEmail());
						clt.setPhone(client.getPhone());
						clt.setUsername(client.getUsername());
						clt.setPassword(client.getPassword());
						return clientRepo.save(clt);
					})
					.orElseGet(() -> {
						return clientRepo.save(client);
					});
	  }
	  public int removeClient(String memberShip){
			return clientRepo.deleteByMembershipnum(memberShip);
	  }
	  public boolean isActive(String memberShip){
		  return clientRepo.findStatusByMembershipnum(memberShip).getStatus();
	  }

}
