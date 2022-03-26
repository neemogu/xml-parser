package ru.fit.nsu.np.openmap.controller;

import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Getter
public class ErrorResponse {
    private final long timestamp;
    private final List<Pair<String, String>> errors;

    public ErrorResponse(List<Pair<String, String>> errors) {
        this.timestamp = System.currentTimeMillis();
        this.errors = errors;
    }

    public ErrorResponse(String field, String description) {
        this(Collections.singletonList(Pair.of(field, description)));
    }
}
