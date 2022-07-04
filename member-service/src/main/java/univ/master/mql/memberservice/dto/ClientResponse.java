package univ.master.mql.memberservice.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientResponse {
	private Long id;
	private String membership;
	private String fName;
	private String lName;
	private String cin;
	private boolean status;
	private Date dateOfBirth;
	private Date inscriptionDate;
	private String gender;
	private String address;
	private String email;
	private String phone;
	private String	username;
	private String  password;
	
}
