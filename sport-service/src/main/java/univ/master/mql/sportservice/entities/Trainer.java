package univ.master.mql.sportservice.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "trainer_id")
    private Long id;
    @Column
    private String fName;

    //	@ApiModelProperty(notes = "Client's last name")
    @Column
    private String lName;

    //	@ApiModelProperty(notes = "Client's CIN number")
    @Column
    private String cin;

    //	@ApiModelProperty(notes = "Trainer's gender")
    @Column
    private String gender;

    //	@ApiModelProperty(notes = "Trainer's Address")
    @Column
    private String address;

    //	@ApiModelProperty(notes = "Trainer's email")
    @Column
    private String email;

    //	@ApiModelProperty(notes = "Trainer's phone number")
    @Column
    private String phone;

    //	@ApiModelProperty(notes = "Trainer's date of birth")
    @Column
    private Date dateOfBirth;


    //	@ApiModelProperty(notes = "Trainer's  availability")
    @Column
    private Boolean availability;

    //	@ApiModelProperty(notes = "Trainer's Login")
    @Column( unique=true)
    private String	username;

    //	@ApiModelProperty(notes = "Trainer's  password")
    @Column
    private String  password;

//    @JsonBackReference

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "sport_id")
    private Sport sport;

    @JsonIgnore
    @OneToMany(mappedBy = "trainer",targetEntity = Group.class)
    private List<Group> groupList;

}
