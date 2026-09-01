package io.github.victorrot44.forge.web.autoconfigure.jackson.mixin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.victorrot44.forge.web.core.response.SuccessResponse;
import org.springframework.boot.jackson.JacksonMixin;

@JacksonMixin(SuccessResponse.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public abstract class SuccessResponseMixin {

    @JsonInclude(JsonInclude.Include.ALWAYS)
    public abstract Object data();

}
