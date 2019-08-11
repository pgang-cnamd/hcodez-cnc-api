package com.hcodez.cncapi.user;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private static final String JWT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyLCJ1c2VybmFtZSI6ImNlemFybWF0aGUifQ.zXWOgUOnmaj5ZwW9M8mKCU6iDNmXHHxhcBTSX-vhXv0";

    public Optional<String> getOwner(String jwt) {
        return jwt.equals(JWT) ? Optional.of("cezarmathe") : Optional.empty();
    }
}
