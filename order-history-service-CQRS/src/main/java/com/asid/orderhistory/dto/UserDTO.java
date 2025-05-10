package com.asid.orderhistory.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String fullname;
    private String username;
    private String email;
}
