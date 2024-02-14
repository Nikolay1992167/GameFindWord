package by.solbeg.service;

import by.solbeg.exception.ValidateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FindWordServiceTest {

    private FindWordService findWordService;

    @BeforeEach
    public void setUp() {
        findWordService = new FindWordService();
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForTest")
    public void shouldReturnExpectedValue(String entered, String expected) {
        // given, when
        String actual = findWordService.outputByCharacters(entered);

        // then
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    public void shouldNotThrowException() {

        assertThatCode(() -> findWordService.find("ooрог"))
                .doesNotThrowAnyException();
    }

    @Test
    public void shouldReturnExceptionWithInvalidLength() {

        assertThatThrownBy(() -> findWordService.find("гор"))
                .isInstanceOf(ValidateException.class)
                .hasMessage("Длина слова должна равняться 5!");
    }

    @Test
    public void shouldReturnExceptionWithNonLetterCharacters() {

        assertThatThrownBy(() -> findWordService.find("гор1д"))
                .isInstanceOf(ValidateException.class)
                .hasMessage("Слово должно состоять из букв!");
    }

    @Test
    public void shouldReturnExceptionWithNull() {

        assertThatThrownBy(() -> findWordService.find(null))
                .isInstanceOf(ValidateException.class)
                .hasMessage("Значение не может быть null или пустым!");
    }

    @Test
    public void shouldReturnExceptionWithEmptyString() {

        assertThatThrownBy(() -> findWordService.find(""))
                .isInstanceOf(ValidateException.class)
                .hasMessage("Значение не может быть null или пустым!");
    }

    static Stream<Arguments> getArgumentsForTest() {

        return Stream.of(
                Arguments.of("город", "\u001B[32mг\u001B[0m" +
                                      "\u001B[32mо\u001B[0m" +
                                      "\u001B[32mр\u001B[0m" +
                                      "\u001B[32mо\u001B[0m" +
                                      "\u001B[32mд\u001B[0m"),
                Arguments.of("ооооо", "\u001B[90mо\u001B[0m" +
                                      "\u001B[32mо\u001B[0m" +
                                      "\u001B[90mо\u001B[0m" +
                                      "\u001B[32mо\u001B[0m" +
                                      "\u001B[90mо\u001B[0m"),
                Arguments.of("порог", "\u001B[90mп\u001B[0m" +
                                      "\u001B[32mо\u001B[0m" +
                                      "\u001B[32mр\u001B[0m" +
                                      "\u001B[32mо\u001B[0m" +
                                      "\u001B[33mг\u001B[0m")
        );
    }
}