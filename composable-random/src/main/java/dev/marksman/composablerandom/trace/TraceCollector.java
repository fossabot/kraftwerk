package dev.marksman.composablerandom.trace;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

import static com.jnape.palatable.lambda.functions.builtin.fn2.Cons.cons;
import static java.util.Collections.emptyList;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TraceCollector {
    private static final TraceCollector EMPTY = traceCollector(emptyList());

    private final Iterable<Trace<Object>> collectedTraces;

    public TraceCollector add(Trace<Object> trace) {
        return traceCollector(cons(trace, collectedTraces));
    }

    public static TraceCollector traceCollector(Iterable<Trace<Object>> collectedTraces) {
        return new TraceCollector(collectedTraces);
    }

    public static TraceCollector traceCollector() {
        return EMPTY;
    }
}
