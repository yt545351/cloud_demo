package com.example.system.entity;

import lombok.Data;

import java.math.BigDecimal;


/**
 * 人
 *
 * @author laughlook
 * @date 2022/07/21
 */
@Data
public class Person {
    private double a;
    private Integer b;
    private BigDecimal c;
    private int d;
    private double e;
    private double f;

    public Person() {
    }

    public Person(double a, Integer b, BigDecimal c, int d, double e, double f) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public Integer getB() {
        return b;
    }

    public void setB(Integer b) {
        this.b = b;
    }

    public BigDecimal getC() {
        return c;
    }

    public void setC(BigDecimal c) {
        this.c = c;
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    public double getE() {
        return e;
    }

    public void setE(double e) {
        this.e = e;
    }

    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }
}
