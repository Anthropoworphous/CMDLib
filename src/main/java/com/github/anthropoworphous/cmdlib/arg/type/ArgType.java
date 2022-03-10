package com.github.anthropoworphous.cmdlib.arg.type;

import com.github.anthropoworphous.cmdlib.processor.parser.ArgParser;
import com.github.anthropoworphous.cmdlib.processor.parser.ModifiableParser;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Consumer;

public interface ArgType<T> {
    /**
     * Get the parser of this argType instance
     * You can edit the parser from this, it'll have an effect
     * @return The parser
     */
    @NotNull ArgParser<T> parser();
    default ArgType<T> parser(@NotNull Consumer<ModifiableParser<T>> modifier) {
        modifier.accept(parser());
        return this;
    }

    @NotNull Optional<T> stringToArgType(String input);
    @NotNull String argTypeToString(T input);

    @NotNull String readableName();
    @NotNull String readableDescription();
}
