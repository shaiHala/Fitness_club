package univ.master.mql.administrationservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    private String dateOfBirth;
    private String gender;


}
