package com.rentals.apartment.service.filter;

import java.util.Objects;

public record RangeFilter(
        Double minimum,
        Double maximum
) {
    public RangeFilter(Double minimum, Double maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public Double getMinimum() {
        return minimum;
    }

    public Double getMaximum() {
        return maximum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RangeFilter that = (RangeFilter) o;
        return Objects.equals(minimum, that.minimum) && Objects.equals(maximum, that.maximum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(minimum, maximum);
    }
}
