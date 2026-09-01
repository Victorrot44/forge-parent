package io.github.victorrot44.forge.web.autoconfigure.jackson.mixin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.victorrot44.forge.web.core.response.ErrorResponse;
import org.springframework.boot.jackson.JacksonMixin;

@JacksonMixin(ErrorResponse.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public abstract class ErrorResponseMixin {

}
