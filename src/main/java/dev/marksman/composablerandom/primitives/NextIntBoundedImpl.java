package dev.marksman.composablerandom.primitives;

import dev.marksman.composablerandom.GeneratorState;
import dev.marksman.composablerandom.RandomState;
import dev.marksman.composablerandom.Result;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NextIntBoundedImpl implements GeneratorState<Integer> {
    private final int bound;

    @Override
    public Result<? extends RandomState, Integer> run(RandomState input) {
        return input.nextIntBounded(bound);
    }

    public static NextIntBoundedImpl nextIntBoundedImpl(int bound) {
        return new NextIntBoundedImpl(bound);
    }
}
