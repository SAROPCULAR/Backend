package com.sarop.saropbackend.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenClaims {
    JWT_ID("jti"),
    TYPE("typ"),
    USER_ID("userId"),
    NAME("name"),


    MAIL("mail"),
    USER_STATUS("userStatus"),
    ROLE_NAME("roleName"),
    SCOPE("scope"),
    SUBJECT("sub"),


    ISSUED_AT("iat"),
    EXPIRES_AT("exp"),
    ALGORITHM("alg"),


    TYPE_VAL("JWT"),
    ISSUER("sarop"),



    ;
    private final String value;
}
