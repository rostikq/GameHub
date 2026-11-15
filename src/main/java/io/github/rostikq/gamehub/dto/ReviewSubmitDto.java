package io.github.rostikq.gamehub.dto;

import org.jspecify.annotations.NonNull;

import java.math.BigDecimal;

public record ReviewSubmitDto(String title, String description, @NonNull BigDecimal rating, long gameId) {
}
