package com.itsm.parse;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class JsonResponse implements Response{
    @JsonProperty
    private String message;

    public JsonResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JsonResponse)) return false;
        JsonResponse that = (JsonResponse) o;
        return Objects.equals(getMessage(), that.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMessage());
    }
}
