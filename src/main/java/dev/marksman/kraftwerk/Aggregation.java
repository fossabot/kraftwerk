package dev.marksman.kraftwerk;

import com.jnape.palatable.lambda.adt.Maybe;
import com.jnape.palatable.lambda.functions.builtin.fn2.Map;
import dev.marksman.kraftwerk.aggregator.Aggregator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import static com.jnape.palatable.lambda.functions.builtin.fn2.Replicate.replicate;
import static dev.marksman.kraftwerk.Result.result;

class Aggregation {

    static <A, Builder, Out> Generator<Out> aggregate(Aggregator<A, Builder, Out> aggregator,
                                                      Iterable<Generator<A>> elements) {
        return new Aggregate<>(aggregator, elements);
    }

    static <A, Builder, Out> Generator<Out> aggregate(Aggregator<A, Builder, Out> aggregator,
                                                      int size,
                                                      Generator<A> gen) {
        return new Aggregate<>(aggregator, replicate(size, gen));
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    static class Aggregate<Elem, Builder, Out> implements Generator<Out> {
        private static Maybe<String> LABEL = Maybe.just("aggregate");

        private final Aggregator<Elem, Builder, Out> aggregator;
        private final Iterable<Generator<Elem>> elements;

        @Override
        public Generate<Out> prepare(Parameters parameters) {
            Iterable<Generate<Elem>> runners = Map.map(g -> g.prepare(parameters), elements);
            return input -> {
                Seed current = input;
                Builder builder = aggregator.builder();

                for (Generate<Elem> element : runners) {
                    Result<? extends Seed, Elem> next = element.apply(current);
                    builder = aggregator.add(builder, next.getValue());
                    current = next.getNextState();
                }
                return result(current, aggregator.build(builder));
            };

        }

        @Override
        public Maybe<String> getLabel() {
            return LABEL;
        }

    }
}
