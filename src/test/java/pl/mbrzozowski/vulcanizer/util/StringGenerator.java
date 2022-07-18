package pl.mbrzozowski.vulcanizer.util;

import java.util.function.Function;

/**
 * String Generator with specific length
 * <p>
 * Length
 * MAX_LENGTH = 1000,
 * MIN_LENGTH = 0
 * <p>
 * Return Sequence of chars like string
 */
public class StringGenerator implements Function<Integer, String> {

    /**
     * @param length the function argument
     * @return Sequence of chars like string
     */
    @Override
    public String apply(Integer length) {
        StringBuilder result = new StringBuilder();
        final int MAX_LENGTH = 1000;
        if (length <= 0) {
            return result.toString();
        }
        if (length > MAX_LENGTH) {
            length = MAX_LENGTH;
        }
        result.append("a".repeat(length));
        return String.valueOf(result);

    }
}
