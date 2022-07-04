package univ.master.mql.memberservice.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
//import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id")
public class Client {
//	@ApiModelProperty(notes = "The database generated client ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "client_id")
	private Long id;
	@Column( unique=true)
	private String membershipnum;

//	@ApiModelProperty(notes = "Client's first name")
	@Column
	private String fName;

//	@ApiModelProperty(notes = "Client's last name")
	@Column
	private String lName;

//	@ApiModelProperty(notes = "Client's CIN number")
	@Column
	private String cin;

//	@ApiModelProperty(notes = "Client's gender")
	@Column
	private String gender;

//	@ApiModelProperty(notes = "Client's Address")
	@Column
	private String address;

//	@ApiModelProperty(notes = "Client's email")
	@Column
	private String email;

//	@ApiModelProperty(notes = "Client's phone number")
	@Column
	private String phone;

//	@ApiModelProperty(notes = "Client's date of birth")
	@Column
	private Date dateOfBirth;

//	@ApiModelProperty(notes = "Client's inscription date")
	@Column
	private Date inscriptionDate;

//	@ApiModelProperty(notes = "Client's  acount status")
//
	@Column
	private String status;
	@Column
	private int statusCode;

//	@ApiModelProperty(notes = "Client's Login")
	@Column( unique=true)
	private String	username;

//	@ApiModelProperty(notes = "Client's  password status")
	@Column
	private String  password;

	@OneToMany(mappedBy = "client",targetEntity = BodyMeasurements.class)
//	@JsonManagedReference (value="client- bodyMeasurements")
	@JsonIgnore
	private List<BodyMeasurements> bodyMeasurementsList;

	@OneToMany(mappedBy = "client",targetEntity = Attendance.class)
	private List<Attendance> attendanceList;

	@OneToMany(mappedBy = "client",targetEntity = MemberShip.class)
	private List<MemberShip> memberShipList;


}
