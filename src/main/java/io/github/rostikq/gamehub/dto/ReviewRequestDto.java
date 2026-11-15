package io.github.rostikq.gamehub.dto;

import org.jspecify.annotations.NonNull;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public record ReviewRequestDto(long id, String title, String description, @NonNull String authorUsername, @NonNull String gameName, long gameId, @NonNull ZonedDateTime time, @NonNull BigDecimal rate) {
}
