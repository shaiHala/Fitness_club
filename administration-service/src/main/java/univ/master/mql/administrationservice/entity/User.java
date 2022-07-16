package univ.master.mql.administrationservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @Column(unique = true)
    private String id;
    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;
    @Column
    private String password;
    @Column
    private String firstname;
    @Column
    private String lastname;
    @Column
    private String phone;
    @Column
    private int statusCode;
    @Column
    private String status;
    @Column
    private String birthdate;
    @Column
    private String gender;
    @Column
    private String address;

    @Column(unique = true)
    private String cin;
}
