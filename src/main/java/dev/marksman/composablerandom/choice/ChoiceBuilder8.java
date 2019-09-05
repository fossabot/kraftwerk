package dev.marksman.composablerandom.choice;

import com.jnape.palatable.lambda.adt.choice.Choice8;
import dev.marksman.composablerandom.Generator;
import dev.marksman.composablerandom.ToGenerator;
import dev.marksman.composablerandom.frequency.FrequencyMap;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChoiceBuilder8<A, B, C, D, E, F, G, H> implements ToGenerator<Choice8<A, B, C, D, E, F, G, H>> {
    private final FrequencyMap<Choice8<A, B, C, D, E, F, G, H>> frequencyMap;

    @Override
    public Generator<Choice8<A, B, C, D, E, F, G, H>> toGenerator() {
        return frequencyMap.toGenerator();
    }

    public static <A, B, C, D, E, F, G, H> ChoiceBuilder8<A, B, C, D, E, F, G, H> choiceBuilder8(FrequencyMap<Choice8<A, B, C, D, E, F, G, H>> frequencyMap) {
        return new ChoiceBuilder8<>(frequencyMap);
    }

}
