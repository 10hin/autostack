package autostack.impl;

import java.util.NoSuchElementException;
import java.util.function.Function;

import autostack.AutoStack;

public class AutoStackImpl<H, R extends AutoStack<?, ?>>  implements AutoStack<H, R> {

    private final H head;
    private final R rest;

    private AutoStackImpl(H head, R rest) {
        this.head = head;
        this.rest = rest;
    }

    @Override
    public <N> AutoStack<N, AutoStack<H, R>> push(N newHead) {
        return new AutoStackImpl<>(newHead, this);
    }

    @Override
    public <N> AutoStack<N, AutoStack<H, R>> push(Function<? super H, ? extends N> func) {
        return this.push(func.apply(this.head));
    }

    @Override
    public H head() {
        return this.head;
    }

    @Override
    public R pop() {
        return this.rest;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public static AutoStack<?, ?> ofEmpty() {
        return EmptyStack.of();
    }

    public static <H> AutoStack<H, ?> of(H head) {
        return EmptyStack.of().push(head);
    }

    private static class EmptyStack implements AutoStack<Object, EmptyStack> {

        private static final EmptyStack EMPTY_STACK = new EmptyStack();

        private EmptyStack() {
            // do nothing;
        }

        @Override
        public <N> AutoStack<N, AutoStack<Object, EmptyStack>> push(N newHead) {
            return new AutoStackImpl<>(newHead, this);
        }

        @Override
        public <N> AutoStack<N, AutoStack<Object, EmptyStack>> push(Function<? super Object, ? extends N> func) {
            throw new NoSuchElementException("empty stack");
        }

        @Override
        public Object head() {
            throw new NoSuchElementException("empty stack");
        }

        @Override
        public EmptyStack pop() {
            throw new NoSuchElementException("empty stack");
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        public static <H> AutoStack<?, ?> of() {
            return EMPTY_STACK;
        }

    }

}
