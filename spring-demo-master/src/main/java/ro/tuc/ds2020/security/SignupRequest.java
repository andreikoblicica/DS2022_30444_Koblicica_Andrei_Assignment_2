package ro.tuc.ds2020.security;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;


@Data
@Builder
@Setter
public class SignupRequest {
    private String username;
    private String email;
    private String password;

}
