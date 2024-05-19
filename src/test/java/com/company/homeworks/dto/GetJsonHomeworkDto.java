package com.company.homeworks.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@Jacksonized
public class GetJsonHomeworkDto {
    @JsonProperty
    private List<Message> messages;

    @Data
    @Builder
    @Jacksonized
    public static class Message {
        @JsonProperty
        private String message;
        @JsonProperty
        private String timestamp;

    }
}
