package io.github.victorrot44.forge.web.autoconfigure.jackson;

import io.github.victorrot44.forge.web.autoconfigure.jackson.mixin.ForgeWebJacksonMixinPackage;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.jackson.JacksonMixin;

/**
 *
 * @author vrodriguezr
 */
@AutoConfiguration
@ConditionalOnWebApplication
@ConditionalOnClass(JacksonMixin.class)
@AutoConfigurationPackage(basePackageClasses = ForgeWebJacksonMixinPackage.class)
public class ForgeWebJacksonAutoConfiguration {
    
}
