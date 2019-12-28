package com.mhussain.studium.seminargrundlagen;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seminarteilnehmer {

    private Long id;

    private Long matrikelNummer;

    private String name;

    private String email;

    private String seminar;

}