package pl.mbrzozowski.vulcanizer.validation;

import java.util.function.Consumer;

@FunctionalInterface
interface Validator<T> extends Consumer<T> {
}
