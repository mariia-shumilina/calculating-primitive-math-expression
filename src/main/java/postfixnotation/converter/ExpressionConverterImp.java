package postfixnotation.converter;

import postfixnotation.tokenizer.ExpressionTokenizer;
import postfixnotation.validator.ExpressionValidator;
import postfixnotation.validator.ValidatorResponse;

import java.util.Map;
import java.util.Stack;

  
public class ExpressionConverterImp implements ExpressionConverter {
      
    private static final Map<String, Integer> priorityOfOperations = Map.of("+", 3, "-", 3, "/", 2, "*", 2, "^", 1);

    private String processUnaryMinus(String expression) {
        expression = expression.replaceFirst("^-", "0-");
        expression = expression.replaceAll("(\\( *)(-)", "$10$2");
        return expression;
    }

      
    private void validateExpression(String[] expression) {
        ValidatorResponse response;
        String errorMessage = null;
        if ((response = ExpressionValidator.checkBrackets(expression)).getStatusCode() == ValidatorResponse.StatusCode.ERROR) {
            errorMessage = response.getMessage();
        } else if ((response = ExpressionValidator.checkForBadSequence(expression)).getStatusCode() == ValidatorResponse.StatusCode.ERROR) {
            errorMessage = response.getMessage();
        } else if ((response = ExpressionValidator.checkForIllegalToken(expression)).getStatusCode() == ValidatorResponse.StatusCode.ERROR) {
            errorMessage = response.getMessage();
        }
        if (errorMessage != null) throw new IllegalArgumentException(errorMessage);
    }

    @Override
    public String convertToPostfixNotation(String expression) {
        if(expression == null){
            throw new IllegalArgumentException("Expression is null");
        }
        expression = processUnaryMinus(expression);
        String[] tokenizedExpression = ExpressionTokenizer.tokenizeExpression(expression);
        validateExpression(tokenizedExpression);
        Stack<String> operations = new Stack<>();
        StringBuilder reversePolishNotationExpression = new StringBuilder();
        String previousToken = null;
        for (String token : tokenizedExpression) {
            if (ExpressionTokenizer.isNumeric(token)) {
                reversePolishNotationExpression.append(token).append(" ");
            } else if (token.equals("(")) {
                operations.push(token);
            } else if (token.equals(")")) {
                while (!operations.peek().equals("(")) {
                    reversePolishNotationExpression.append(operations.pop()).append(" ");
                }
                operations.pop();
            } else if (priorityOfOperations.containsKey(token)) {
                while (!operations.isEmpty() && !operations.peek().equals("(") && priorityOfOperations.get(operations.peek()) <= priorityOfOperations.get(token)) {
                    reversePolishNotationExpression.append(operations.pop()).append(" ");
                }
                operations.push(token);
            }
            previousToken = token;
        }
        while (!operations.isEmpty()) {
            reversePolishNotationExpression.append(operations.pop()).append(" ");
        }
        return reversePolishNotationExpression.toString();
    }
}
