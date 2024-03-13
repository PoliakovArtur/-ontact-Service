package com.example.model;

import lombok.Builder;

@Builder
public record Contact(long id, String firstName, String lastName, String email, String phoneNumber) {

}
