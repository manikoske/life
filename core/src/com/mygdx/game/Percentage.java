package com.mygdx.game;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by manikoske on 17. 3. 2017.
 */
public class Percentage {

    private BigInteger numerator;
    private BigInteger denominator;

    public Percentage(int numerator, int denominator) {
        this.numerator = BigInteger.valueOf(numerator);
        this.denominator = BigInteger.valueOf(denominator);
    }

    public Percentage(BigInteger numerator, BigInteger denominator) {
        BigInteger gcd = numerator.gcd(denominator);
        this.numerator = numerator.divide(gcd);
        this.denominator = denominator.divide(gcd);
    }

    public Percentage(int numerator) {
        this.numerator = BigInteger.valueOf(numerator);
        this.denominator = BigInteger.valueOf(100);
    }


    public Percentage multiply(Percentage that) {
        return new Percentage(this.numerator.multiply(that.numerator),
                this.denominator.multiply(that.denominator));
    }

    public Percentage add(Percentage that) {
        return new Percentage(this.numerator.multiply(that.denominator).add(that.numerator.multiply(this.denominator)),
                this.denominator.multiply(that.denominator));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Percentage that = (Percentage) o;

        if (!numerator.equals(that.numerator)) return false;
        return denominator.equals(that.denominator);

    }

    @Override
    public int hashCode() {
        int result = numerator.hashCode();
        result = 31 * result + denominator.hashCode();
        return result;
    }

    public int getRoundedRatio() {
        return new BigDecimal(numerator).divide(new BigDecimal(denominator), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).intValue();
    }
}
