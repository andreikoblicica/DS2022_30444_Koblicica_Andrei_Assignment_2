package ro.tuc.ds2020.security;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class JwtResponse {

    private String token;
    private Long id;
    private String username;
    private String role;

}