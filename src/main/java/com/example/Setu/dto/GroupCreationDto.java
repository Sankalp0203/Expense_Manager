package com.example.Setu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class GroupCreationDto {
    private String name;
    private Set<Integer> memberSet;
}
