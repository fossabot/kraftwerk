package dev.marksman.composablerandom.primitives;

import com.jnape.palatable.lambda.functions.Fn3;
import dev.marksman.composablerandom.CompiledGenerator;
import dev.marksman.composablerandom.RandomState;
import dev.marksman.composablerandom.Result;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import static dev.marksman.composablerandom.Result.result;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Product3Impl<A, B, C, Out> implements CompiledGenerator<Out> {
    private final CompiledGenerator<A> a;
    private final CompiledGenerator<B> b;
    private final CompiledGenerator<C> c;
    private final Fn3<A, B, C, Out> combine;

    @Override
    public Result<? extends RandomState, Out> run(RandomState input) {
        Result<? extends RandomState, A> r1 = a.run(input);
        Result<? extends RandomState, B> r2 = b.run(r1.getNextState());
        Result<? extends RandomState, C> r3 = c.run(r2.getNextState());
        Out result = combine.apply(r1.getValue(), r2.getValue(), r3.getValue());
        return result(r3.getNextState(), result);
    }

    public static <A, B, C, Out> Product3Impl<A, B, C, Out> product3Impl(CompiledGenerator<A> a,
                                                                         CompiledGenerator<B> b,
                                                                         CompiledGenerator<C> c,
                                                                         Fn3<A, B, C, Out> combine) {
        return new Product3Impl<>(a, b, c, combine);
    }

}