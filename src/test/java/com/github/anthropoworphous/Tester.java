package com.github.anthropoworphous;

import com.github.anthropoworphous.cmdlib.arg.analyst.implementation.Analyst;
import com.github.anthropoworphous.cmdlib.arg.route.implementation.Route;
import com.github.anthropoworphous.cmdlib.arg.type.ArgType;
import com.github.anthropoworphous.cmdlib.arg.type.lonetypes.implementation.types.BooleanVar;
import com.github.anthropoworphous.cmdlib.arg.type.lonetypes.implementation.types.IntVar;
import com.github.anthropoworphous.cmdlib.arg.type.lonetypes.implementation.types.StringVar;
import com.github.anthropoworphous.cmdlib.arg.type.multitypes.implementation.types.CustomMultiValueVar;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class Tester {
    @Test
    public void routeCompressionTest() {
        CustomMultiValueVar<String> TestStrVar1 = new CustomMultiValueVar<String>("e", "e") {
            @Override
            public List<ArgType<?>> divideInto() {
                return Arrays.asList(
                        new StringVar(),
                        new StringVar(),
                        new StringVar()
                );
            }

            @Override
            public Optional<String> combineInto(List<Object> input) {
                return Optional.of(input.get(2).toString() +
                        input.get(1).toString() +
                        input.get(0).toString()
                );
            }
        };
        CustomMultiValueVar<String> TestStrVar2 = new CustomMultiValueVar<String>("e", "e") {
            @Override
            public List<ArgType<?>> divideInto() {
                return Arrays.asList(
                        new StringVar(),
                        TestStrVar1,
                        new StringVar()
                );
            }

            @Override
            public Optional<String> combineInto(List<Object> input) {
                return Optional.of(input.get(2).toString() +
                        input.get(1).toString() +
                        input.get(0).toString()
                );
            }
        };
        CustomMultiValueVar<String> TestStrVar3 = new CustomMultiValueVar<String>("e", "e") {
            @Override
            public List<ArgType<?>> divideInto() {
                return Arrays.asList(
                        new StringVar(),
                        TestStrVar2,
                        new StringVar()
                );
            }

            @Override
            public  Optional<String> combineInto( List<Object> input) {
                return Optional.of(input.get(2).toString() +
                        input.get(1).toString() +
                        input.get(0).toString()
                );
            }
        };


        assertEquals(
                new Route(TestStrVar3)
                        .compress(Arrays.asList("g", "f", "e", "d", "c", "b", "a"))
                        .get(0)
                        .getValue()
                        .toString()
                ,"abcdefg"
        );
    }

    @Test
    public void cmdAutoCompleteTest() {
        List<String> list = new Analyst(
                Arrays.asList(
                        "123",
                        "abc",
                        "f"
                ),
                Arrays.asList(
                        new Route(
                                new IntVar(),
                                new StringVar(),
                                new BooleanVar()
                        ),
                        new Route(
                                new BooleanVar(),
                                new StringVar(),
                                new IntVar()
                        )
                )
        ).getAutoFill();

        assertEquals(list.size(), 1);
        assertEquals(list.get(0), "false");
    }


}
