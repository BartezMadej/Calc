package com.clc.calc.calculator;


public class Calculator {
	private StringBuilder equation;
	private NumbersStack numbersStack;
	private OperatorsStack operatorsStack;

	public Calculator(String equation) {
		numbersStack = new NumbersStack();
		operatorsStack = new OperatorsStack();
		equation = equation.replaceAll("\\s", "");
		this.equation = new StringBuilder(equation);
	}

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

	public double getNumber() {
		int negative = 1;
		if (equation.charAt(0) == '-') {
			negative = -1;
			equation.deleteCharAt(0);
		}
		int i = 0;
		double res = 0.0;
		while (i < equation.length() && Character.isDigit(equation.charAt(i)) )
			res = res * 10 + (equation.charAt(i++) - 48);

		if (i< equation.length() && equation.charAt(i) == '.') {
			double coef = 0.1;
			int counter = 0;
			while (Character.isDigit(equation.charAt(++i)) && i < equation.capacity()) {
				res = res + coef * (equation.charAt(i) - 48);
				coef /= 10;
				++counter;
			}
			res = Math.round(res * Math.pow(10, counter)) / Math.pow(10, counter);
		}
		equation.delete(0, i);
		res *= negative;
		return res;
	}

	public void setEquation(String equation) {
		this.equation.setLength(0);
		this.equation.append(equation.replaceAll("\\s", ""));
	}

	public int operatorPriority(char value) {
		switch (value) {
			case '(':
			case ')':
				return 1;
			case '-':
			case '+':
				return 2;
			case '*':
			case '/':
				return 3;
			case '^':
				return 4;
			default:
				return 0;
		}
	}

	public double computeEquation() {
		char c;
		int i = 0;
		double var = 0.0;

		if (Character.isDigit(this.equation.charAt(i)))
			numbersStack.pushNumber(getNumber());
		while (i<this.equation.length() && operatorsStack.isOperator((c = this.equation.charAt(i)))) {
			this.equation.deleteCharAt(i);
			if (c == ')') {
				while ((c = operatorsStack.popOperator()) != '(') {
					var = numbersStack.popNumber();
					numbersStack.pushNumber(singleOperatorOperations(c, numbersStack.popNumber(), var));
				}

			} else {
				while (operatorPriority(c) <= operatorPriority(operatorsStack.topOperator())) {
					var = numbersStack.popNumber();
					numbersStack.pushNumber(singleOperatorOperations(operatorsStack.popOperator(), var, numbersStack.popNumber()));
				}
				operatorsStack.pushOperator(c);
				while((c=this.equation.charAt(i))== '(') {
					operatorsStack.pushOperator(c);
					this.equation.deleteCharAt(i);
				}
					numbersStack.pushNumber(getNumber());
			}

		}
		while (!operatorsStack.isEmpty()) {
			var = numbersStack.popNumber();
			numbersStack.pushNumber(singleOperatorOperations(operatorsStack.popOperator(), numbersStack.popNumber(),var));
		}
		var = numbersStack.popNumber();
		numbersStack.clearStack();
		operatorsStack.clearStack();
		return var;
	}

}