package com.clc.calc.calculator;

import java.util.Stack;

public class OperatorsStack {
	private Stack<Character> operatorsStack = new Stack<>();

	public OperatorsStack() {
	}

	public void pushOperator(char value) {
		if (isOperator(value))
			operatorsStack.push(value);
	}

	public char popOperator() {
		return operatorsStack.pop();
	}

	public char topOperator() {
		return operatorsStack.peek();
	}

	public boolean isEmpty() {
		return operatorsStack.empty();
	}

	public boolean isOperator(char value) {
		return value == '-' || value == '+' || value == '*' || value == '/' || value == '^' || value == '(' || value == ')';
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
}
