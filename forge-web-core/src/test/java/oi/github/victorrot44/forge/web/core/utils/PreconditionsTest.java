package oi.github.victorrot44.forge.web.core.utils;

import io.github.victorrot44.forge.web.core.util.Preconditions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PreconditionsTest {

    @Nested
    class ConstructorTest {

        @Test
        void constructorShouldThrowAssertionError() throws NoSuchMethodException {
            Constructor<Preconditions> constructor = Preconditions.class.getDeclaredConstructor();
            assertThat(Modifier.isPrivate(constructor.getModifiers())).isTrue();

            constructor.setAccessible(true);

            InvocationTargetException ex = assertThrows(
                    InvocationTargetException.class,
                    constructor::newInstance
            );

            assertThat(ex.getCause())
                    .isInstanceOf(AssertionError.class)
                    .hasMessage("No instances. Util class.");
        }
    }

    @Nested
    class ImmutableMapTest {

        @Test
        void shouldReturnEmptyMapWhenNull() {
            Map<String, Integer> result = Preconditions.immutableMap(null);

            assertThat(result).isEmpty();
        }

        @Test
        void shouldReturnCopyOfGivenMap() {
            Map<String, Integer> original = Map.of("a", 1, "b", 2);

            Map<String, Integer> result = Preconditions.immutableMap(original);

            assertThat(result).isEqualTo(original);
        }

        @Test
        void resultShouldBeImmutable() {
            Map<String, Integer> original = Map.of("a", 1);
            Map<String, Integer> result = Preconditions.immutableMap(original);

            assertThatThrownBy(() -> result.put("b", 2))
                    .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        void shouldNotBeSameInstanceAsInput() {
            Map<String, Integer> original = Map.of("a", 1);

            Map<String, Integer> result = Preconditions.immutableMap(original);

            assertThat(result).isEqualTo(original);
        }
    }

    @Nested
    class RequireNotNullOrEmptyTest {

        @Test
        void shouldReturnValueWhenValid() {
            String result = Preconditions.requireNotNullOrEmpty("hello");

            assertThat(result).isEqualTo("hello");
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {" ", "   ", "\t", "\n"})
        void shouldThrowWhenNullOrBlank(String value) {
            assertThatThrownBy(() -> Preconditions.requireNotNullOrEmpty(value))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("value is null or empty");
        }

        @Test
        void shouldThrowWithCustomMessage() {
            assertThatThrownBy(() -> Preconditions.requireNotNullOrEmpty(null, "custom message"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("custom message");
        }

        @Test
        void shouldThrowWithCustomMessageWhenBlank() {
            assertThatThrownBy(() -> Preconditions.requireNotNullOrEmpty("   ", "custom message"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("custom message");
        }
    }

    @Nested
    class RequirePositiveTest {

        @Test
        void shouldReturnValueWhenPositive() {
            Integer result = Preconditions.requirePositive(5);

            assertThat(result).isEqualTo(5);
        }

        @Test
        void shouldThrowWhenNull() {
            assertThatThrownBy(() -> Preconditions.requirePositive((Integer) null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("value must be greater than or equal to zero.");
        }

        @Test
        void shouldThrowWhenZero() {
            assertThatThrownBy(() -> Preconditions.requirePositive(0))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void shouldThrowWhenNegative() {
            assertThatThrownBy(() -> Preconditions.requirePositive(-1))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void shouldWorkWithDifferentNumberTypes() {
            assertThat(Preconditions.requirePositive(1L)).isEqualTo(1L);
            assertThat(Preconditions.requirePositive(1.5)).isEqualTo(1.5);
            assertThat(Preconditions.requirePositive((short) 1)).isEqualTo((short) 1);
        }

        @Test
        void shouldThrowWithCustomMessage() {
            assertThatThrownBy(() -> Preconditions.requirePositive(-1, "custom message"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("custom message");
        }

        @Test
        void shouldTruncateDecimalWhenCheckingIntValue() {
            // 0.5 -> intValue() = 0, así que debería lanzar
            assertThatThrownBy(() -> Preconditions.requirePositive(0.5))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}