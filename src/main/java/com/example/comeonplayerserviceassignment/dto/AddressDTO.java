package com.example.comeonplayerserviceassignment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO
{
    private String addressLine1;
    private String addressLine2;
    private String postCode;
    private String city;
}
