package com.rentals.apartment.service.filter;

import java.util.Objects;

public class RangeFilter {
    private Double minimum;
    private Double maximum;

    public RangeFilter() {
    }

    public RangeFilter(Double minimum, Double maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public Double getMinimum() {
        return minimum;
    }

    public void setMinimum(Double minimum) {
        this.minimum = minimum;
    }

    public Double getMaximum() {
        return maximum;
    }

    public void setMaximum(Double maximum) {
        this.maximum = maximum;
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
