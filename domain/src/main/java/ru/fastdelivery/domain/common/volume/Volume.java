package ru.fastdelivery.domain.common.volume;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public record Volume(BigDecimal volumeCubicMillimeters) implements Comparable<Volume> {

    public static Volume zero() {
        return new Volume(BigDecimal.ZERO);
    }

    public Volume add(Volume additionalVolume) {
        return new Volume(this.volumeCubicMillimeters.add(additionalVolume.volumeCubicMillimeters));
    }


    public BigDecimal cubicMeters() {
        return volumeCubicMillimeters
                .divide(BigDecimal.valueOf(1000_000_000), 4, RoundingMode.HALF_UP);
    }

    public boolean greaterThan(Volume v) {
        return volumeCubicMillimeters().compareTo(v.volumeCubicMillimeters()) > 0;
    }

    @Override
    public int compareTo(Volume v) {
        return volumeCubicMillimeters().compareTo(v.volumeCubicMillimeters());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Volume volume = (Volume) o;
        return Objects.equals(volumeCubicMillimeters, volume.volumeCubicMillimeters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(volumeCubicMillimeters);
    }
}
