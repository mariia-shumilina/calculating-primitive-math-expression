package postfixnotation.calculator;

import postfixnotation.converter.ExpressionConverter;
import postfixnotation.converter.ExpressionConverterImp;
import postfixnotation.tokenizer.ExpressionTokenizer;

import java.util.Stack;

 
public class ExpressionCalculatorImp implements ExpressionCalculator {
     
    private ExpressionConverter expressionConverter;

    public ExpressionCalculatorImp() {
        expressionConverter = new ExpressionConverterImp();
    }

    public double calculateExpression(String expression) {
        String polishNotationExpression = expressionConverter.convertToPostfixNotation(expression);
        String[] tokenizedExpression = polishNotationExpression.split(" ");
        Stack<Double> stack = new Stack<>();
        for (String token : tokenizedExpression) {
            if (!ExpressionTokenizer.isOperator(token)) {
                Double operand = Double.parseDouble(token);
                stack.push(operand);
            } else {
                Double rightOperand = stack.pop();
                Double leftOperand = stack.pop();
                switch (token) {
                    case "+" -> stack.push(leftOperand + rightOperand);
                    case "-" -> stack.push(leftOperand - rightOperand);
                    case "*" -> stack.push(leftOperand * rightOperand);
                    case "/" -> {
                        if (rightOperand.equals(0.0)) {
                            throw new ArithmeticException("Division by zero");
                        }
                        stack.push(leftOperand / rightOperand);
                    }
                    case "^" -> stack.push(Math.pow(leftOperand, rightOperand));
                }
            }
        }
        return stack.pop();
    }
}
