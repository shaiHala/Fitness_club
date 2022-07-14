package univ.master.mql.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDTO {
	private String fName;
	private String lName;
	private String membershipNum;
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
	public Boolean getStatus() {
		return status;
	}
}
