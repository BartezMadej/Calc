package com.clc.calc.test;

import com.clc.calc.calculator.Calculator;

public class Test {
	public static void main(String[] args) {
		Calculator calc= new Calculator("2*(3+6*2)+(((5^2)))");
		System.out.println(calc.computeEquation());

	}
}
