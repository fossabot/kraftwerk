package dev.marksman.kraftwerk.frequency;

import com.jnape.palatable.lambda.functions.Fn1;
import dev.marksman.kraftwerk.FrequencyEntry;
import dev.marksman.kraftwerk.Generator;

import static dev.marksman.kraftwerk.FrequencyEntry.entry;
import static dev.marksman.kraftwerk.Generators.generateLongIndex;
import static dev.marksman.kraftwerk.frequency.FrequencyMap1.checkMultiplier;
import static dev.marksman.kraftwerk.frequency.FrequencyMapN.addLabel;
import static dev.marksman.kraftwerk.frequency.FrequencyMapN.frequencyMapN;
import static java.util.Arrays.asList;

final class FrequencyMap3<A> implements FrequencyMap<A> {
    private final int weightA;
    private final Generator<A> generatorA;
    private final int weightB;
    private final Generator<A> generatorB;
    private final int weightC;
    private final Generator<A> generatorC;

    @SuppressWarnings("unchecked")
    private FrequencyMap3(int weightA, Generator<? extends A> generatorA,
                          int weightB, Generator<? extends A> generatorB,
                          int weightC, Generator<? extends A> generatorC) {
        this.weightA = weightA;
        this.generatorA = (Generator<A>) generatorA;
        this.weightB = weightB;
        this.generatorB = (Generator<A>) generatorB;
        this.weightC = weightC;
        this.generatorC = (Generator<A>) generatorC;
    }

    static <A> FrequencyMap3<A> frequencyMap3(int weightA, Generator<? extends A> generatorA,
                                              int weightB, Generator<? extends A> generatorB,
                                              int weightC, Generator<? extends A> generatorC) {
        return new FrequencyMap3<>(weightA, generatorA, weightB, generatorB, weightC, generatorC);
    }

    @Override
    public Generator<A> toGenerator() {
        long thresholdB = weightA + weightB;
        return addLabel(generateLongIndex(weightA + weightB + weightC)
                .flatMap(n -> n < weightA
                        ? generatorA
                        : n < thresholdB
                        ? generatorB
                        : generatorC));
    }

    @Override
    public FrequencyMap<A> combine(FrequencyMap<A> other) {
        return other.add(weightA, generatorA)
                .add(weightB, generatorB)
                .add(weightC, generatorC);
    }

    @Override
    public FrequencyMap<A> add(int weight, Generator<? extends A> gen) {
        if (weight < 1) {
            return this;
        } else {
            //noinspection unchecked
            return frequencyMapN((FrequencyEntry<A>) entry(weight, gen),
                    asList(entry(weightA, generatorA), entry(weightB, generatorB), entry(weightC, generatorC)));
        }
    }

    @Override
    public FrequencyMap<A> multiply(int positiveFactor) {
        checkMultiplier(positiveFactor);
        if (positiveFactor == 1) return this;
        else return frequencyMap3(positiveFactor * weightA, generatorA,
                positiveFactor * weightB, generatorB,
                positiveFactor * weightC, generatorC);
    }

    @Override
    public <B> FrequencyMap<B> fmap(Fn1<? super A, ? extends B> fn) {
        return frequencyMap3(weightA, generatorA.fmap(fn),
                weightB, generatorB.fmap(fn),
                weightC, generatorC.fmap(fn));
    }
}
