package br.com.xxzidanilloxx.teammanagementapi.enumeration;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Gender {
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    public static Gender fromString(String text) {
        return Arrays.stream(Gender.values())
                .filter(g -> g.name().equalsIgnoreCase(text))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
