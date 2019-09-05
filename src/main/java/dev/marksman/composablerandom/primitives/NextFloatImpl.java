package dev.marksman.composablerandom.primitives;

import dev.marksman.composablerandom.GeneratorState;
import dev.marksman.composablerandom.RandomState;
import dev.marksman.composablerandom.Result;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NextFloatImpl implements GeneratorState<Float> {
    private static NextFloatImpl INSTANCE = new NextFloatImpl();

    @Override
    public Result<? extends RandomState, Float> run(RandomState input) {
        return input.nextFloat();
    }

    public static NextFloatImpl nextFloatImpl() {
        return INSTANCE;
    }
}
