package com.clc.calc.calculator;

public class Calculator {
	public double singleOperatorOperations(char operator, double val1, double val2) {
		switch (operator) {
			case '-':
				return val1 - val2;
			case '+':
				return val1 + val2;
			case '*':
				return val1 * val2;
			case '/':
				if (val2 > 1e-14)
					return val1 / val2;
				else
					throw new ArithmeticException();
			case '^':
				return Math.pow(val1, val2);
			default:
				return 0;
		}
	}
}
