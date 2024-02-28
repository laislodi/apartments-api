package com.rentals.apartment.service.filter;

import java.util.Objects;

public class MinimumFilter {
    private Integer minimum;
    private boolean greaterThan;

    public MinimumFilter() {
    }

    public MinimumFilter(Integer minimum, boolean greaterThan) {
        this.minimum = minimum;
        this.greaterThan = greaterThan;
    }

    public Integer getMinimum() {
        return minimum;
    }

    public void setMinimum(Integer minimum) {
        this.minimum = minimum;
    }

    public boolean isGreaterThan() {
        return greaterThan;
    }

    public void setGreaterThan(boolean greaterThan) {
        this.greaterThan = greaterThan;
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
