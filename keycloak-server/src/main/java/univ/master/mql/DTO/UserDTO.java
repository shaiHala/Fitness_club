package univ.master.mql.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String id;
    private String email;
    private String password;
    private String firstname;
    private String username;
    private String lastname;
    private String phone;
    private int statusCode;
    private String status;
    private String birthdate;
    private String gender;
    private String cin;
    private String address;

}
