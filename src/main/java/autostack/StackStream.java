package autostack;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class StackStream<H, R extends AutoStack<?, ?>> implements Stream<H> {

    private final Stream<AutoStack<H, R>> stream;

    private StackStream() {
        this.stream = Stream.empty();
    }

    private StackStream(Stream<AutoStack<H, R>> stream) {
        this.stream = stream;
    }

    @Override
    public Iterator<H> iterator() {
        return this.stream.map(AutoStack<H, R>::head).iterator();
    }

    @Override
    public Spliterator<H> spliterator() {
        return this.stream.map(AutoStack<H, R>::head).spliterator();
    }

    @Override
    public boolean isParallel() {
        return this.stream.isParallel();
    }

    @Override
    public StackStream<H, R> sequential() {
        return new StackStream<>(this.stream.sequential());
    }

    @Override
    public StackStream<H, R> parallel() {
        return new StackStream<>(this.stream.parallel());
    }

    @Override
    public StackStream<H, R> unordered() {
        return new StackStream<H, R>(this.stream.unordered());
    }

    @Override
    public StackStream<H, R> onClose(Runnable closeHandler) {
        return new StackStream<H, R>(this.stream.onClose(closeHandler));
    }

    @Override
    public void close() {
        this.stream.close();
    }

    @Override
    public StackStream<H, R> filter(Predicate<? super H> predicate) {
        return new StackStream<H, R>(this.stream.filter(stk -> predicate.test(stk.head())));
    }

    @Override
    public <T> StackStream<T, R> map(Function<? super H, ? extends T> mapper) {
        return new StackStream<T, R>(this.stream.map(new Function<AutoStack<H, R>, AutoStack<T, R>>() {
            public AutoStack<T, R> apply(AutoStack<H, R> stk) {
                H head = stk.head();
                T mapped = mapper.apply(head);
                R rest = stk.pop();

                @SuppressWarnings("unchecked")
                AutoStack<T, R> ret = (AutoStack<T, R>) rest.push(mapped);

                return ret;
            }
        }));
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super H> mapper) {
        // TODO 自動生成されたメソッド・スタブ
        throw new UnsupportedOperationException("unsupported");
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super H> mapper) {
        // TODO 自動生成されたメソッド・スタブ
        throw new UnsupportedOperationException("unsupported");
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super H> mapper) {
        // TODO 自動生成されたメソッド・スタブ
        throw new UnsupportedOperationException("unsupported");
    }

    @Override
    public <T> Stream<T> flatMap(Function<? super H, ? extends Stream<? extends T>> mapper) {
        return new StackStream<T, R>(this.stream.flatMap(new Function<AutoStack<H, R>, Stream<? extends AutoStack<T, R>>>() {
            public Stream<AutoStack<T, R>> apply(AutoStack<H, R> stk) {
                H head = stk.head();
                Stream<? extends T> mapped = mapper.apply(head);
                R rest = stk.pop();
                @SuppressWarnings("unchecked")
                Stream<AutoStack<T, R>> ret = mapped.map(e -> (AutoStack<T, R>) rest.push((T) e));
                return ret;
            }
        }));
    }

    @Override
    public IntStream flatMapToInt(Function<? super H, ? extends IntStream> mapper) {
        // TODO 自動生成されたメソッド・スタブ
        throw new UnsupportedOperationException("unsupported");
    }

    @Override
    public LongStream flatMapToLong(Function<? super H, ? extends LongStream> mapper) {
        // TODO 自動生成されたメソッド・スタブ
        throw new UnsupportedOperationException("unsupported");
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super H, ? extends DoubleStream> mapper) {
        // TODO 自動生成されたメソッド・スタブ
        throw new UnsupportedOperationException("unsupported");
    }

    @Override
    public Stream<H> distinct() {
        // TODO 自動生成されたメソッド・スタブ
        throw new UnsupportedOperationException("unsupported");
    }

    @Override
    public StackStream<H, R> sorted() {
        return new StackStream<>(this.stream.sorted(new Comparator<AutoStack<H, R>>() {
            @Override
            public int compare(AutoStack<H, R> o1, AutoStack<H, R> o2) {
                @SuppressWarnings("unchecked")
                Comparable<H> c1 = (Comparable<H>) o1.head();
                H h2 = o2.head();
                return c1.compareTo(h2);
            }
        }));
    }

    @Override
    public Stream<H> sorted(Comparator<? super H> comparator) {
        return new StackStream<>(this.stream.sorted(Comparator.comparing(AutoStack<H, R>::head, comparator)));
    }

    @Override
    public StackStream<H, R> peek(Consumer<? super H> action) {
        return new StackStream<>(this.stream.peek(new Consumer<AutoStack<H, R>>() {
            @Override
            public void accept(AutoStack<H, R> stk) {
                action.accept(stk.head());
            }
        }));
    }

    @Override
    public Stream<H> limit(long maxSize) {
        return new StackStream<>(this.stream.limit(maxSize));
    }

    @Override
    public Stream<H> skip(long n) {
        return new StackStream<>(this.stream.skip(n));
    }

    @Override
    public void forEach(Consumer<? super H> action) {
        this.stream.forEach(new Consumer<AutoStack<H, R>>() {
            @Override
            public void accept(AutoStack<H, R> stk) {
                action.accept(stk.head());
            }
        });
    }

    @Override
    public void forEachOrdered(Consumer<? super H> action) {
        this.stream.forEachOrdered(new Consumer<AutoStack<H, R>>() {
            @Override
            public void accept(AutoStack<H, R> stk) {
                action.accept(stk.head());
            }
        });
    }

    @Override
    public Object[] toArray() {
        return this.stream.map(AutoStack<H, R>::head).toArray();
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        return this.stream.map(AutoStack<H, R>::head).toArray(generator);
    }

    @Override
    public H reduce(H identity, BinaryOperator<H> accumulator) {
        return this.stream.map(AutoStack<H, R>::head).reduce(identity, accumulator);
    }

    @Override
    public Optional<H> reduce(BinaryOperator<H> accumulator) {
        return this.stream.map(AutoStack<H, R>::head).reduce(accumulator);
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super H, U> accumulator, BinaryOperator<U> combiner) {
        return this.stream.map(AutoStack<H, R>::head).reduce(identity, accumulator, combiner);
    }

    @Override
    public <T> T collect(Supplier<T> supplier, BiConsumer<T, ? super H> accumulator, BiConsumer<T, T> combiner) {
        return this.stream.map(AutoStack<H, R>::head).collect(supplier, accumulator, combiner);
    }

    @Override
    public <T, A> T collect(Collector<? super H, A, T> collector) {
        return this.stream.map(AutoStack<H, R>::head).collect(collector);
    }

    @Override
    public Optional<H> min(Comparator<? super H> comparator) {
        return this.stream.map(AutoStack<H, R>::head).min(comparator);
    }

    @Override
    public Optional<H> max(Comparator<? super H> comparator) {
        return this.stream.map(AutoStack<H, R>::head).max(comparator);
    }

    @Override
    public long count() {
        return this.stream.map(AutoStack<H, R>::head).count();
    }

    @Override
    public boolean anyMatch(Predicate<? super H> predicate) {
        return this.stream.map(AutoStack<H, R>::head).anyMatch(predicate);
    }

    @Override
    public boolean allMatch(Predicate<? super H> predicate) {
        return this.stream.map(AutoStack<H, R>::head).allMatch(predicate);
    }

    @Override
    public boolean noneMatch(Predicate<? super H> predicate) {
        return this.stream.map(AutoStack<H, R>::head).noneMatch(predicate);
    }

    @Override
    public Optional<H> findFirst() {
        return this.stream.map(AutoStack<H, R>::head).findFirst();
    }

    @Override
    public Optional<H> findAny() {
        return this.stream.map(AutoStack<H, R>::head).findAny();
    }

    public static <H, R extends AutoStack<?, ?>> Collector<H, ?, StackStream<H, R>> stacking() {
        return Collector.of(StackStream::empty, new BiConsumer<StackStream<H, R>, H>() {
            @Override
            public void accept(StackStream<H, R> stkStream, H head) {
            }
        }, new BinaryOperator<StackStream<H, R>>() {
            @Override
            public StackStream<H, R> apply(StackStream<H, R> stkStream1, StackStream<H, R> stkStream2) {
                return new StackStream<>(Stream.concat(stkStream1.stream, stkStream2.stream));
            }
        });
    }

    public static <H, R extends AutoStack<?, ?>> StackStream<H, R> empty() {
        return new StackStream<>();
    }

}
