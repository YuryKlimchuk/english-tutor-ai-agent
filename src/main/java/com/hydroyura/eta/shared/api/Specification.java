package com.hydroyura.eta.shared.api;

public interface Specification<T> {

    boolean isSatisfiedBy(T candidate);

    default Specification<T> and(Specification<? super T> other) {
        return t -> this.isSatisfiedBy(t) && other.isSatisfiedBy(t);
    }

    default Specification<T> or(Specification<? super T> other) {
        return t -> this.isSatisfiedBy(t) || other.isSatisfiedBy(t);
    }

    default Specification<T> not() {
        return t -> !this.isSatisfiedBy(t);
    }
}
