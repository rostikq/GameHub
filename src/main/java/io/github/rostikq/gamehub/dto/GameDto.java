package io.github.rostikq.gamehub.dto;

public record GameDto(
        String name,
        String description,
        int publicYear,
        String genre,
        String imageUrl) {
}
