package com.clc.calc.calculator;

import java.util.Stack;

public class NumbersStack {
	Stack<Double> numbersStack = new Stack<>();

	public NumbersStack() {
	}

	public void pushNumber(double value) {
		numbersStack.push(value);
	}

	public double popNumber()
	{
		return numbersStack.pop();
	}

	public Double topNumber() {
		return numbersStack.peek();
	}

	public boolean isEmpty() {
		return numbersStack.empty();
	}

	public void clearStack() {
		numbersStack.clear();
	}

}
