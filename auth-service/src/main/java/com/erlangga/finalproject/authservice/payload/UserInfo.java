package com.erlangga.finalproject.authservice.payload;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserInfo {
    public Long id;
    public String username;
    public String email;
    public String role;
    private boolean is_registered;
}
