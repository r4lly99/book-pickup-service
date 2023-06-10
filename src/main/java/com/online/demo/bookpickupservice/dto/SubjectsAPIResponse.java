package com.online.demo.bookpickupservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SubjectsAPIResponse {

    private String name;
    private List<Works> works;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Works {

        private String key;
        private String title;
        private Integer editionCount;
        private List<Authors> authors;
        private Availability availability;

        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class Authors {
            private String name;
        }

        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class Availability {
            private String isbn;
            private String status;
            private Boolean availableToBorrow;
        }

    }

}
