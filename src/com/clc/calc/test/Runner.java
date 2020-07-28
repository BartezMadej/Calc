package com.clc.calc.test;

import com.clc.calc.calculator.Calculator;
import com.clc.calc.gui.CalcGui;

public class Runner
{
	public static void main(String[] args) {
		Calculator calc= new Calculator("((2*(5*40)+5/23.333)/233+5)");
		CalcGui gui=new CalcGui();
		CalcGui.DigitAndOperatorListener var=  gui.new DigitAndOperatorListener();
		gui.showWindow();
		System.out.println(var.validateEquation("((2*(5*40)+5/23)/233+5)"));
	}
}
