package com.example.springboot.jpa.data.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@NoArgsConstructor
public class ChangeProductNameDto {

    private String name;

    public ChangeProductNameDto(String name) {
        this.name = name;
    }

}
