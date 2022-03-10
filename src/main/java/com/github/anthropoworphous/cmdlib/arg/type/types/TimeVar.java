package com.github.anthropoworphous.cmdlib.arg.type.types;

import com.github.anthropoworphous.cmdlib.arg.type.BaseTypes;
import com.github.anthropoworphous.cmdlib.processor.parser.ArgParser;
import com.github.anthropoworphous.cmdlib.processor.parser.BaseArgParser;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Convert time spam to tick and back
 */
public class TimeVar extends BaseTypes<Long> {
    public TimeVar() {
        super("<Time>", "420s, 69day, support t/s/m/h/d/week/month/year");
    }

    @Override
    public @NotNull Optional<Long> stringToArgType(String input) {
        Matcher match = Pattern.compile("(\\d+\\.?\\d*)\\s?([A-z]+)").matcher(input);

        if (match.find()) {
            for (TimeUnits u : TimeUnits.values()) {
                if (u.check(match.group(2).toLowerCase())) {
                    return Optional.of((long) (Double.parseDouble(match.group(1)) * u.multiplier));
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public @NotNull String argTypeToString(Long input) {
        double tick = input;
        String[] unit = {"t", "s", "m", "h", "days", "weeks", "months", "years"};
        long[] unitLength = { 1, 20, 1200, 72000, 1728000, 12096000, 362880000, 4354560000L};
        for (int i = 0; i < unit.length; i++) {
            if (tick / unitLength[i] <= 1000) {
                return Math.round(tick / unitLength[i] * 100) / 100.0 + unit[i];
            }
        }
        return Math.round(tick / unitLength[unit.length - 1] * 100) / 100.0 + unit[unit.length - 1];
    }

    @Override
    @NotNull
    public ArgParser<Long> parser() {
        return new BaseArgParser<>(this);
    }

    private enum TimeUnits {
        TICK(1, "t", "tick", "ticks"),
        SECOND(20, "s", "sec", "second", "seconds"),
        MINUTE(1200, "m", "min", "minute", "minutes"),
        HOUR(72000, "h", "hr", "hour", "hours"),
        DAY(1728000, "d", "day", "days"),
        WEEK(12096000, "w", "week", "weeks"),
        MONTH(362880000, "mon", "month", "months"),
        YEAR(4354560000L, "y", "year", "years");

        TimeUnits(long multiplier, String... alias) {
            this.multiplier = multiplier;
            this.alias = alias;
        }

        long multiplier;
        String[] alias;

        public boolean check(String input) {
            return Arrays.asList(alias).contains(input);
        }
    }
}
