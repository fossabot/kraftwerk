package dev.marksman.composablerandom;

import static com.jnape.palatable.lambda.functions.builtin.fn2.Map.map;
import static dev.marksman.composablerandom.primitives.AggregateImpl.aggregateImpl;
import static dev.marksman.composablerandom.primitives.ConstantImpl.constantImpl;
import static dev.marksman.composablerandom.primitives.CustomImpl.customImpl;
import static dev.marksman.composablerandom.primitives.FlatMappedImpl.flatMappedImpl;
import static dev.marksman.composablerandom.primitives.MappedImpl.mappedImpl;
import static dev.marksman.composablerandom.primitives.NextBooleanImpl.nextBooleanImpl;
import static dev.marksman.composablerandom.primitives.NextByteImpl.nextByteImpl;
import static dev.marksman.composablerandom.primitives.NextBytesImpl.nextBytesImpl;
import static dev.marksman.composablerandom.primitives.NextDoubleImpl.nextDoubleImpl;
import static dev.marksman.composablerandom.primitives.NextFloatImpl.nextFloatImpl;
import static dev.marksman.composablerandom.primitives.NextGaussianImpl.nextGaussianImpl;
import static dev.marksman.composablerandom.primitives.NextIntBetweenImpl.nextIntBetweenImpl;
import static dev.marksman.composablerandom.primitives.NextIntBoundedImpl.nextIntBoundedImpl;
import static dev.marksman.composablerandom.primitives.NextIntExclusiveImpl.nextIntExclusiveImpl;
import static dev.marksman.composablerandom.primitives.NextIntImpl.nextIntImpl;
import static dev.marksman.composablerandom.primitives.NextIntIndexImpl.nextIntIndexImpl;
import static dev.marksman.composablerandom.primitives.NextLongBetweenImpl.nextLongBetweenImpl;
import static dev.marksman.composablerandom.primitives.NextLongBoundedImpl.nextLongBoundedImpl;
import static dev.marksman.composablerandom.primitives.NextLongExclusiveImpl.nextLongExclusiveImpl;
import static dev.marksman.composablerandom.primitives.NextLongImpl.nextLongImpl;
import static dev.marksman.composablerandom.primitives.NextLongIndexImpl.nextLongIndexImpl;
import static dev.marksman.composablerandom.primitives.NextShortImpl.nextShortImpl;
import static dev.marksman.composablerandom.primitives.Product2Impl.product2Impl;
import static dev.marksman.composablerandom.primitives.Product3Impl.product3Impl;
import static dev.marksman.composablerandom.primitives.Product4Impl.product4Impl;
import static dev.marksman.composablerandom.primitives.Product5Impl.product5Impl;
import static dev.marksman.composablerandom.primitives.Product6Impl.product6Impl;
import static dev.marksman.composablerandom.primitives.Product7Impl.product7Impl;
import static dev.marksman.composablerandom.primitives.Product8Impl.product8Impl;
import static dev.marksman.composablerandom.primitives.SizedImpl.sizedImpl;
import static dev.marksman.composablerandom.primitives.TapImpl.tapImpl;

public class DefaultInterpreter implements Interpreter {
    @Override
    public <A> GeneratorState<A> handle(InterpreterContext context, Generator<A> gen) {
        if (gen instanceof Generator.Constant) {
            return constantImpl(((Generator.Constant<A>) gen).getValue());
        }

        if (gen instanceof Generator.Custom) {
            return customImpl(((Generator.Custom<A>) gen).getFn());
        }

        if (gen instanceof Generator.Mapped) {
            return handleMapped(context, (Generator.Mapped<?, A>) gen);
        }

        if (gen instanceof Generator.FlatMapped) {
            return handleFlatMapped(context, (Generator.FlatMapped<?, A>) gen);
        }

        if (gen instanceof Generator.Tap) {
            return handleTap(context, (Generator.Tap<?, A>) gen);
        }

        if (gen instanceof Generator.NextInt) {
            //noinspection unchecked
            return (GeneratorState<A>) nextIntImpl();
        }

        if (gen instanceof Generator.NextLong) {
            //noinspection unchecked
            return (GeneratorState<A>) nextLongImpl();
        }

        if (gen instanceof Generator.NextBoolean) {
            //noinspection unchecked
            return (GeneratorState<A>) nextBooleanImpl();
        }

        if (gen instanceof Generator.NextDouble) {
            //noinspection unchecked
            return (GeneratorState<A>) nextDoubleImpl();
        }

        if (gen instanceof Generator.NextFloat) {
            //noinspection unchecked
            return (GeneratorState<A>) nextFloatImpl();
        }

        if (gen instanceof Generator.NextIntBounded) {
            int bound = ((Generator.NextIntBounded) gen).getBound();
            //noinspection unchecked
            return (GeneratorState<A>) nextIntBoundedImpl(bound);
        }

        if (gen instanceof Generator.NextIntExclusive) {
            Generator.NextIntExclusive g1 = (Generator.NextIntExclusive) gen;
            //noinspection unchecked
            return (GeneratorState<A>) nextIntExclusiveImpl(g1.getOrigin(), g1.getBound());
        }

        if (gen instanceof Generator.NextIntBetween) {
            Generator.NextIntBetween g1 = (Generator.NextIntBetween) gen;
            //noinspection unchecked
            return (GeneratorState<A>) nextIntBetweenImpl(g1.getMin(), g1.getMax());
        }

        if (gen instanceof Generator.NextIntIndex) {
            Generator.NextIntIndex g1 = (Generator.NextIntIndex) gen;
            //noinspection unchecked
            return (GeneratorState<A>) nextIntIndexImpl(g1.getBound());
        }

        if (gen instanceof Generator.NextLongBounded) {
            Generator.NextLongBounded g1 = (Generator.NextLongBounded) gen;
            //noinspection unchecked
            return (GeneratorState<A>) nextLongBoundedImpl(g1.getBound());
        }

        if (gen instanceof Generator.NextLongExclusive) {
            Generator.NextLongExclusive g1 = (Generator.NextLongExclusive) gen;
            //noinspection unchecked
            return (GeneratorState<A>) nextLongExclusiveImpl(g1.getOrigin(), g1.getBound());
        }

        if (gen instanceof Generator.NextLongBetween) {
            Generator.NextLongBetween g1 = (Generator.NextLongBetween) gen;
            //noinspection unchecked
            return (GeneratorState<A>) nextLongBetweenImpl(g1.getMin(), g1.getMax());
        }

        if (gen instanceof Generator.NextLongIndex) {
            Generator.NextLongIndex g1 = (Generator.NextLongIndex) gen;
            //noinspection unchecked
            return (GeneratorState<A>) nextLongIndexImpl(g1.getBound());
        }

        if (gen instanceof Generator.NextGaussian) {
            //noinspection unchecked
            return (GeneratorState<A>) nextGaussianImpl();
        }

        if (gen instanceof Generator.NextByte) {
            //noinspection unchecked
            return (GeneratorState<A>) nextByteImpl();
        }

        if (gen instanceof Generator.NextShort) {
            //noinspection unchecked
            return (GeneratorState<A>) nextShortImpl();
        }

        if (gen instanceof Generator.NextBytes) {
            Generator.NextBytes g1 = (Generator.NextBytes) gen;
            //noinspection unchecked
            return (GeneratorState<A>) nextBytesImpl(g1.getCount());
        }

        if (gen instanceof Generator.WithMetadata) {
            Generator.WithMetadata g1 = (Generator.WithMetadata) gen;
            //noinspection unchecked
            return context.recurse(g1.getOperand());
        }

        if (gen instanceof Generator.Sized) {
            Generator.Sized g1 = (Generator.Sized) gen;

            //noinspection unchecked
            return sizedImpl(context.getParameters().getSizeSelector(),
                    rs -> context.recurse((Generator<A>) g1.getFn().apply(rs)));
        }

        if (gen instanceof Generator.Aggregate) {
            Generator.Aggregate g1 = (Generator.Aggregate) gen;
            //noinspection unchecked
            Iterable<Generator<A>> elements = g1.getElements();

            //noinspection unchecked
            return (GeneratorState<A>) aggregateImpl(g1.getInitialBuilderSupplier(), g1.getAddFn(),
                    g1.getBuildFn(), map(context::recurse, elements));
        }

        if (gen instanceof Generator.Product2) {
            Generator.Product2 g1 = (Generator.Product2) gen;
            //noinspection unchecked
            return (GeneratorState<A>) product2Impl(context.recurse(g1.getA()),
                    context.recurse(g1.getB()),
                    g1.getCombine());
        }

        if (gen instanceof Generator.Product3) {
            Generator.Product3 g1 = (Generator.Product3) gen;
            //noinspection unchecked
            return (GeneratorState<A>) product3Impl(context.recurse(g1.getA()),
                    context.recurse(g1.getB()),
                    context.recurse(g1.getC()),
                    g1.getCombine());
        }

        if (gen instanceof Generator.Product4) {
            Generator.Product4 g1 = (Generator.Product4) gen;
            //noinspection unchecked
            return (GeneratorState<A>) product4Impl(context.recurse(g1.getA()),
                    context.recurse(g1.getB()),
                    context.recurse(g1.getC()),
                    context.recurse(g1.getD()),
                    g1.getCombine());
        }

        if (gen instanceof Generator.Product5) {
            Generator.Product5 g1 = (Generator.Product5) gen;
            //noinspection unchecked
            return (GeneratorState<A>) product5Impl(context.recurse(g1.getA()),
                    context.recurse(g1.getB()),
                    context.recurse(g1.getC()),
                    context.recurse(g1.getD()),
                    context.recurse(g1.getE()),
                    g1.getCombine());
        }

        if (gen instanceof Generator.Product6) {
            Generator.Product6 g1 = (Generator.Product6) gen;
            //noinspection unchecked
            return (GeneratorState<A>) product6Impl(context.recurse(g1.getA()),
                    context.recurse(g1.getB()),
                    context.recurse(g1.getC()),
                    context.recurse(g1.getD()),
                    context.recurse(g1.getE()),
                    context.recurse(g1.getF()),
                    g1.getCombine());
        }

        if (gen instanceof Generator.Product7) {
            Generator.Product7 g1 = (Generator.Product7) gen;
            //noinspection unchecked
            return (GeneratorState<A>) product7Impl(context.recurse(g1.getA()),
                    context.recurse(g1.getB()),
                    context.recurse(g1.getC()),
                    context.recurse(g1.getD()),
                    context.recurse(g1.getE()),
                    context.recurse(g1.getF()),
                    context.recurse(g1.getG()),
                    g1.getCombine());
        }

        if (gen instanceof Generator.Product8) {
            Generator.Product8 g1 = (Generator.Product8) gen;
            //noinspection unchecked
            return (GeneratorState<A>) product8Impl(context.recurse(g1.getA()),
                    context.recurse(g1.getB()),
                    context.recurse(g1.getC()),
                    context.recurse(g1.getD()),
                    context.recurse(g1.getE()),
                    context.recurse(g1.getF()),
                    context.recurse(g1.getG()),
                    context.recurse(g1.getH()),
                    g1.getCombine());
        }

        return context.callNextHandler(gen);
    }

    private <In, Out> GeneratorState<Out> handleMapped(InterpreterContext context,
                                                       Generator.Mapped<In, Out> mapped) {
        return mappedImpl(mapped.getFn(), context.recurse(mapped.getOperand()));
    }

    private <In, Out> GeneratorState<Out> handleFlatMapped(InterpreterContext context,
                                                           Generator.FlatMapped<In, Out> flatMapped) {
        return flatMappedImpl(in -> context.recurse(flatMapped.getFn().apply(in)),
                context.recurse(flatMapped.getOperand()));
    }

    private <In, Out> GeneratorState<Out> handleTap(InterpreterContext context, Generator.Tap<In, Out> gen) {
        return tapImpl(context.recurse(gen.getInner()), gen.getFn());
    }

    public static DefaultInterpreter defaultInterpreter() {
        return new DefaultInterpreter();
    }

}
