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
        // TODO 自動生成されたメソッド・スタブ
        return this;
    }

    @Override
    public StackStream<H, R> parallel() {
        // TODO 自動生成されたメソッド・スタブ
        return this;
    }

    @Override
    public StackStream<H, R> unordered() {
        // TODO 自動生成されたメソッド・スタブ
        return this;
    }

    @Override
    public Stream<H> onClose(Runnable closeHandler) {
        this.stream.onClose(closeHandler);
        return this;
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
                Stream<AutoStack<T, R>> ret = mapped.map(e -> rest.push((T) e));
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
        return null;
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super H, ? extends DoubleStream> mapper) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public Stream<H> distinct() {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public Stream<H> sorted() {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public Stream<H> sorted(Comparator<? super H> comparator) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public Stream<H> peek(Consumer<? super H> action) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public Stream<H> limit(long maxSize) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public Stream<H> skip(long n) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public void forEach(Consumer<? super H> action) {
        // TODO 自動生成されたメソッド・スタブ
        
    }

    @Override
    public void forEachOrdered(Consumer<? super H> action) {
        // TODO 自動生成されたメソッド・スタブ
        
    }

    @Override
    public Object[] toArray() {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public H reduce(H identity, BinaryOperator<H> accumulator) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public Optional<H> reduce(BinaryOperator<H> accumulator) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super H, U> accumulator, BinaryOperator<U> combiner) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super H> accumulator, BiConsumer<R, R> combiner) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public <R, A> R collect(Collector<? super H, A, R> collector) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public Optional<H> min(Comparator<? super H> comparator) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public Optional<H> max(Comparator<? super H> comparator) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public long count() {
        // TODO 自動生成されたメソッド・スタブ
        return 0;
    }

    @Override
    public boolean anyMatch(Predicate<? super H> predicate) {
        // TODO 自動生成されたメソッド・スタブ
        return false;
    }

    @Override
    public boolean allMatch(Predicate<? super H> predicate) {
        // TODO 自動生成されたメソッド・スタブ
        return false;
    }

    @Override
    public boolean noneMatch(Predicate<? super H> predicate) {
        // TODO 自動生成されたメソッド・スタブ
        return false;
    }

    @Override
    public Optional<H> findFirst() {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public Optional<H> findAny() {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    public static <T> Collector<T, ?, StackStream<T, ?>> stacking() {
        return Collector.of(StackStream.<T>empty(), null, null);
    }

}
