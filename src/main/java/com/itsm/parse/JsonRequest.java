package com.itsm.parse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.Objects;

@JsonPropertyOrder({"name", "message"})
public class JsonRequest {
    @JsonProperty("name")
    private String name;

    @JsonProperty("message")
    private String message;

    public JsonRequest() {
    }

    public JsonRequest(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public JsonRequest setName(String name) {
        this.name = name;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public JsonRequest setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JsonRequest that = (JsonRequest) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, message);
    }
}
