package br.com.xxzidanilloxx.teammanagementapi.dto;

import br.com.xxzidanilloxx.teammanagementapi.enumeration.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record StudentResponseDTO(
        Long id,
        String name,
        String cpf,
        String email,
        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate birthDate,
        Gender gender,
        String courseName,
        String ra,
        boolean status) {
}
