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
		if (!operatorsStack.empty())
			return operatorsStack.peek();
		return ' ';
	}

	public boolean isEmpty() {
		return operatorsStack.empty();
	}

	public void clearStack() {
		operatorsStack.clear();
	}

	public boolean isOperator(char value) {
		return value == '-' || value == '+' || value == '*' || value == '/' || value == '^' || value == '(' || value == ')';
	}


}
