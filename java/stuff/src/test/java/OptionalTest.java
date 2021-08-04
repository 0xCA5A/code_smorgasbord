import org.junit.jupiter.api.Test;

import java.util.Optional;

public class OptionalTest {

    class Book {
    }

    @Test
    void orElse_when_emptyThenGeneratorFunctionCalled() {
        final Book nullBook = null;
        final Book book = Optional.ofNullable(nullBook).orElse(generatorFunction());
    }

    @Test
    void orElse_when_presentThenGeneratorFunctionCalled() {
        final Book nonNullBook = new Book();
        final Book book = Optional.ofNullable(nonNullBook).orElse(generatorFunction());
    }

    @Test
    void orElseGet_when_emptyThenGeneratorFunctionCalled() {
        final Book nullBook = null;
        final Book book = Optional.ofNullable(nullBook).orElseGet(this::generatorFunction);
    }

    @Test
    void orElseGet_when_presentThenGeneratorFunctionNotCalled() {
        final Book nonNullBook = new Book();
        final Book book = Optional.ofNullable(nonNullBook).orElseGet(this::generatorFunction);
    }

    private Book generatorFunction() {
        System.out.println(">>>> generatorFunction called");
        return new Book();
    }
}
