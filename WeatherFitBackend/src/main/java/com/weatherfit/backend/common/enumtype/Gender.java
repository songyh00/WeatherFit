package com.weatherfit.backend.common.enumtype;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 사용자 및 옷 성별 구분을 위한 Enum
 */
public enum Gender {
    MALE("남자"),
    FEMALE("여자"),
    UNISEX("공용");

    private final String description;

    Gender(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }

    @JsonCreator
    public static Gender from(String input) {
        for (Gender gender : Gender.values()) {
            if (gender.name().equalsIgnoreCase(input) || gender.description.equals(input)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("지원하지 않는 성별입니다: " + input);
    }

}