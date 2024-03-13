package com.example.dto;

import lombok.Builder;

@Builder
public record ContactDto(String firstName, String lastName, String email, String phoneNumber) {

}
