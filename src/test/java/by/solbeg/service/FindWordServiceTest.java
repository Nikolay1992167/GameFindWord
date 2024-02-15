package by.solbeg.service;

import by.solbeg.enam.Color;
import by.solbeg.exception.ValidateException;
import by.solbeg.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
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
    public void shouldReturnExpectedValue(String entered, List<Response> expected) {
        // given, when
        List<Response> actual = findWordService.outputByCharacters(entered);

        // then
        for (int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i).getPosition()).isEqualTo(expected.get(i).getPosition());
            assertThat(actual.get(i).getColor()).isEqualTo(expected.get(i).getColor());
        }
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
                Arguments.of("город", Arrays.asList(new Response(0, Color.GREEN),
                        new Response(1, Color.GREEN),
                        new Response(2, Color.GREEN),
                        new Response(3, Color.GREEN),
                        new Response(4, Color.GREEN))),
                Arguments.of("ооооо", Arrays.asList(new Response(0, Color.GRAY),
                        new Response(1, Color.GREEN),
                        new Response(2, Color.GRAY),
                        new Response(3, Color.GREEN),
                        new Response(4, Color.GRAY))),
                Arguments.of("порог", Arrays.asList(new Response(0, Color.GRAY),
                        new Response(1, Color.GREEN),
                        new Response(2, Color.GREEN),
                        new Response(3, Color.GREEN),
                        new Response(4, Color.YELLOW)))
        );
    }
}