package com.rentals.apartment.service.filter;

import java.util.Objects;

public record MinimumFilter(
        Integer minimum,
        boolean greaterThan
) {
    public MinimumFilter(Integer minimum, boolean greaterThan) {
        this.minimum = minimum;
        this.greaterThan = greaterThan;
    }

    public Integer getMinimum() {
        return minimum;
    }

    public boolean isGreaterThan() {
        return greaterThan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MinimumFilter that = (MinimumFilter) o;
        return Objects.equals(minimum, that.minimum) && greaterThan == that.greaterThan;
    }

    @Override
    public int hashCode() {
        return Objects.hash(minimum, greaterThan);
    }
}
