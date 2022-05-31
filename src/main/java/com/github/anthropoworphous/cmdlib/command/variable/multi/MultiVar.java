package com.github.anthropoworphous.cmdlib.command.variable.multi;

import com.github.anthropoworphous.cmdlib.command.variable.Var;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public abstract class MultiVar<T> extends Var<T> {
    protected abstract Var<?>[] components();
    protected abstract T convert(Var<?>[] components, Input input);



    // final
    @Override
    public final int size() {
        return Arrays.stream(components()).mapToInt(Var::size).sum();
    }
    @Override
    public final T convert(List<String> input) throws IllegalArgumentException {
        return convert(components(), new Input(input));
    }



    // template
    public abstract static class BiVar<T, V1 extends Var<?>, V2 extends Var<?>> extends MultiVar<T> {
        protected abstract T convert(Input input, V1 var1, V2 var2);
        protected abstract Supplier<V1> getVar1();
        protected abstract Supplier<V2> getVar2();

        @SuppressWarnings("unchecked")
        @Override
        protected final T convert(Var<?>[] components, Input input) {
            return convert(
                    input,
                    (V1) components[0],
                    (V2) components[1]
            );
        }

        @Override
        protected final Var<?>[] components() {
            return new Var[]{ getVar1().get(), getVar2().get() };
        }
    }
    public abstract static class TriVar<T, V1 extends Var<?>, V2 extends Var<?>, V3 extends Var<?>> extends MultiVar<T> {
        protected abstract T convert(Input input, V1 var1, V2 var2, V3 var3);
        protected abstract Supplier<V1> getVar1();
        protected abstract Supplier<V2> getVar2();
        protected abstract Supplier<V3> getVar3();

        @SuppressWarnings("unchecked")
        @Override
        protected final T convert(Var<?>[] components, Input input) {
            return convert(
                    input,
                    (V1) components[0],
                    (V2) components[1],
                    (V3) components[2]
            );
        }

        @Override
        protected final Var<?>[] components() {
            return new Var[]{ getVar1().get(), getVar2().get(), getVar3().get() };
        }
    }
    public abstract static class QuadVar<T, V1 extends Var<?>, V2 extends Var<?>, V3 extends Var<?>, V4 extends Var<?>> extends MultiVar<T> {
        protected abstract T convert(Input input, V1 var1, V2 var2, V3 var3, V4 var4);
        protected abstract Supplier<V1> getVar1();
        protected abstract Supplier<V2> getVar2();
        protected abstract Supplier<V3> getVar3();
        protected abstract Supplier<V4> getVar4();

        @SuppressWarnings("unchecked")
        @Override
        protected final T convert(Var<?>[] components, Input input) {
            return convert(
                    input,
                    (V1) components[0],
                    (V2) components[1],
                    (V3) components[2],
                    (V4) components[3]
            );
        }

        @Override
        protected final Var<?>[] components() {
            return new Var[]{ getVar1().get(), getVar2().get(), getVar3().get(), getVar4().get() };
        }
    }
}
