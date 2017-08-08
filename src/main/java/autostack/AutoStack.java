package autostack;

import java.util.function.Function;

import autostack.impl.AutoStackImpl;

public interface AutoStack<H, R extends AutoStack<?, ?>> {

    <N> AutoStack<N, AutoStack<H, R>> push(N newHead);

    <N> AutoStack<N, AutoStack<H, R>> push(Function<? super H, ? extends N> func);

    H head();

    R pop();

    boolean isEmpty();

    static AutoStack<?, ?> of() {
        return AutoStackImpl.ofEmpty();
    }

    static <H> AutoStack<H, ?> of(H head) {
        return AutoStackImpl.of(head);
    }

}
