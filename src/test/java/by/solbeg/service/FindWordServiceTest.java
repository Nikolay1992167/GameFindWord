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
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FindWordServiceTest {

    private FindWordService findWordService;

    @BeforeEach
    public void setUp() {
        findWordService = new FindWordService();
        findWordService.setHiddenWord("apple");
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForTest")
    public void shouldReturnExpectedValue(String hiddenWord, Map<String, List<Response>> expected) {
        // given
        findWordService.setHiddenWord(hiddenWord);

        for (Map.Entry<String, List<Response>> entry : expected.entrySet()) {
            String enteredWord = entry.getKey();
            List<Response> expectedList = entry.getValue();

            // when
            List<Response> actual = findWordService.outputByCharacters(enteredWord);

            // then
            for (int i = 0; i < actual.size(); i++) {
                assertThat(actual.get(i).getPosition()).isEqualTo(expectedList.get(i).getPosition());
                assertThat(actual.get(i).getColor()).isEqualTo(expectedList.get(i).getColor());
            }
        }
    }

    @Test
    public void shouldNotThrowException() {
        assertThatCode(() -> findWordService.find("denim"))
                .doesNotThrowAnyException();
    }

    @Test
    public void shouldReturnExceptionWithInvalidLength() {
        assertThatThrownBy(() -> findWordService.find("tor"))
                .isInstanceOf(ValidateException.class)
                .hasMessage("The word length should be 5!");
    }

    @Test
    public void shouldReturnExceptionWithNonLetterCharacters() {
        assertThatThrownBy(() -> findWordService.find("den1m"))
                .isInstanceOf(ValidateException.class)
                .hasMessage("The word must consist of letters!");
    }

    @Test
    public void shouldReturnExceptionWithNull() {
        assertThatThrownBy(() -> findWordService.find(null))
                .isInstanceOf(ValidateException.class)
                .hasMessage("The value cannot be null or empty!");
    }

    @Test
    public void shouldReturnExceptionWithEmptyString() {
        assertThatThrownBy(() -> findWordService.find(""))
                .isInstanceOf(ValidateException.class)
                .hasMessage("The value cannot be null or empty!");
    }

    static Stream<Arguments> getArgumentsForTest() {
        return Stream.of(
                Arguments.of("array", Map.of("array", Arrays.asList(new Response(0, Color.GREEN),
                        new Response(1, Color.GREEN),
                        new Response(2, Color.GREEN),
                        new Response(3, Color.GREEN),
                        new Response(4, Color.GREEN)))),
                Arguments.of("style", Map.of("scope", Arrays.asList(new Response(0, Color.GREEN),
                        new Response(1, Color.GRAY),
                        new Response(2, Color.GRAY),
                        new Response(3, Color.GRAY),
                        new Response(4, Color.GREEN)))),
                Arguments.of("start", Map.of("spark", Arrays.asList(new Response(0, Color.GREEN),
                        new Response(1, Color.GRAY),
                        new Response(2, Color.GREEN),
                        new Response(3, Color.GREEN),
                        new Response(4, Color.GRAY)))),
                Arguments.of("close", Map.of("alter", Arrays.asList(new Response(0, Color.GRAY),
                        new Response(1, Color.GREEN),
                        new Response(2, Color.GRAY),
                        new Response(3, Color.YELLOW),
                        new Response(4, Color.GRAY)))),
                Arguments.of("merge", Map.of("green", Arrays.asList(new Response(0, Color.YELLOW),
                        new Response(1, Color.YELLOW),
                        new Response(2, Color.YELLOW),
                        new Response(3, Color.YELLOW),
                        new Response(4, Color.GRAY)))),
                Arguments.of("baaba", Map.of("aabab", Arrays.asList(new Response(0, Color.YELLOW),
                        new Response(1, Color.GREEN),
                        new Response(2, Color.YELLOW),
                        new Response(3, Color.YELLOW),
                        new Response(4, Color.YELLOW))))
                );
    }
}