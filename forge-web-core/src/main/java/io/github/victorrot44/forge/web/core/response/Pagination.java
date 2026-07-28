package io.github.victorrot44.forge.web.core.response;

public record Pagination(
        int page,
        int size,
        long totalItems,
        long totalPages,
        PaginationLinks links
) {

}
