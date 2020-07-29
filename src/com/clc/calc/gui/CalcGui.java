package com.clc.calc.gui;

import com.clc.calc.calculator.Calculator;
import com.clc.calc.calculator.OperatorsStack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class CalcGui
{
	static final char[] signs = {'9', '8', '7', '/', '(', '6', '5', '4', '*', ')', '3', '2', '1', '-', '+', '0', '.', '='};

	private final Calculator calc;
	private final JFrame frame;
	private final JPanel panel;
	private final GridBagConstraints constraints;
	private final JTextField text;
	private final DigitAndOperatorListener actionListener;
	private final KeyListener keyListener;

	public CalcGui()
	{
		calc = new Calculator("");
		actionListener = new DigitAndOperatorListener();
		keyListener = new KeyListener();

		frame = new JFrame("Calculator");
		frame.setResizable(false);
		frame.setSize(300, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		constraints = new GridBagConstraints();
		text = new JTextField();

		setTextEditAndScroll();
		setPaneContent();
		panel.setFocusable(true);
		panel.requestFocusInWindow();
		frame.setContentPane(panel);
	}

	public void setTextEditAndScroll()
	{
		text.setEditable(false);
		text.requestFocus();
		text.setFont(new Font("Dialog", Font.BOLD, 13));
		JScrollBar scrollBar = new JScrollBar(JScrollBar.HORIZONTAL);
		scrollBar.setModel(text.getHorizontalVisibility());


		constraints.weightx = 1.0;
		constraints.weighty = 0.11;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0, 5, 0, 5);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(text, constraints);

		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(scrollBar, constraints);
	}

	public void setPaneContent()
	{
		JButton button = new JButton("CE");
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 2;
		constraints.gridheight = 1;
		constraints.weightx = 0.4;
		constraints.weighty = 0.04;
		button.addActionListener(actionListener);
		panel.add(button, constraints);

		button = new JButton("C");
		button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("BACK_SPACE"), "BackspaceAct");
		button.getActionMap().put("BackspaceAct", keyListener);
		constraints.gridx = 2;
		constraints.gridy = 2;
		button.addActionListener(actionListener);
		panel.add(button, constraints);

		button = new JButton("^");
		button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('^'), "PowAct");
		button.getActionMap().put("PowAct", keyListener);
		constraints.gridx = 4;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 1;
		constraints.weighty = 0.04;
		button.addActionListener(actionListener);
		panel.add(button, constraints);

		int counter = 3;
		for (int i = 0; i < signs.length - 3; ++i)
		{
			button = new JButton("" + signs[i]);
			button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(signs[i]), "act");
			button.getActionMap().put("act", keyListener);
			constraints.gridx = i % 5;
			constraints.gridy = counter;
			button.addActionListener(actionListener);
			if ((i + 1) % 5 == 0)
			{
				++counter;
				constraints.gridwidth = GridBagConstraints.REMAINDER;
			} else
				constraints.gridwidth = 1;
			panel.add(button, constraints);
		}
		button = new JButton("0");
		button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('0'), "0act");
		button.getActionMap().put("0act", keyListener);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 6;
		constraints.gridwidth = 2;
		button.addActionListener(actionListener);
		panel.add(button, constraints);

		button = new JButton(".");
		button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('.'), "DotAct");
		button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(','), "DotAct");
		button.getActionMap().put("DotAct", keyListener);
		constraints.gridx = 2;
		constraints.gridy = 6;
		constraints.gridwidth = 1;
		button.addActionListener(actionListener);
		panel.add(button, constraints);

		button = new JButton("=");
		button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('='), "EqualAction");
		button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "EqualAction");
		button.getActionMap().put("EqualAction", keyListener);
		constraints.gridx = 3;
		constraints.gridy = 6;
		constraints.gridwidth = 2;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		button.addActionListener(actionListener);
		panel.add(button, constraints);
	}

	public void showWindow()
	{
		frame.setVisible(true);
	}

	public class DigitAndOperatorListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent actionEvent)
		{
			if (actionEvent == null)
				return;
			String equation = text.getText();
			String actionStr = actionEvent.getActionCommand();
			if (equation.contains("="))
			{
				text.setText("");
				return;
			}
			char c = actionStr.charAt(0);
			if (Character.isDigit(c) || OperatorsStack.isOperator(c))
				text.setText(equation + actionEvent.getActionCommand());
			else if ((c == '=' || actionStr.equals("\n")) && !equation.equals("") && validateEquation(equation))
			{
				calc.setEquation(text.getText());
				double result = Math.round(calc.computeEquation() * 1e8) / 1e8;
				text.setText("" + result);
			} else if (c == '.' || c == ',')
				text.setText(equation + '.');
			else if (actionStr.equals("CE"))
				text.setText("");
			else if (equation.length() > 0 && (actionStr.equals("C") || actionStr.equals("\b")))
				text.setText(equation.substring(0, equation.length() - 1));
		}

		public boolean validateEquation(String value)
		{
			int openBracket = value.split("\\(", -1).length - 1;
			int closeBracket = value.split("\\)", -1).length - 1;
			boolean correct = value.matches("^\\(*(\\(*(-?[0-9]{1,8}(\\.[0-9]{1,8})?)\\)*[\\-+*/^])+(-?[0-9]{1,8}(\\.[0-9]{1,8})?\\)*)\\)*\\)*$");

			return correct && (openBracket == closeBracket);
		}
	}

	public class KeyListener extends AbstractAction
	{
		DigitAndOperatorListener listener = new DigitAndOperatorListener();

		@Override
		public void actionPerformed(ActionEvent actionEvent)
		{
			listener.actionPerformed(actionEvent);
		}
	}
}
