package io.github.victorrot44.forge.web.core.factory;

public interface ResponseDescriptor {

    int httpStatus();
    String code();
    String message();

}
