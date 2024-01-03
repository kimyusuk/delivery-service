package org.delivery.api.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
