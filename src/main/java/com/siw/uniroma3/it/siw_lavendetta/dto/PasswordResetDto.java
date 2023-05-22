package com.siw.uniroma3.it.siw_lavendetta.dto;

import com.siw.uniroma3.it.siw_lavendetta.models.tokens.PasswordResetToken;

import javax.validation.constraints.NotEmpty;

public class PasswordResetDto {
    @NotEmpty
    private String userEmail;


    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public PasswordResetDto(){}
    public PasswordResetDto(String userEmail,String code) {
        super();
        this.userEmail = userEmail;
        this.code=code;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
