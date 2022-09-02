package postfixnotation.validator;

import postfixnotation.tokenizer.ExpressionTokenizer;

import java.util.EmptyStackException;
import java.util.Stack;
    
public class ExpressionValidator {
        
    public static ValidatorResponse checkBrackets(String[] tokenizedExpression) {
        final String errorMessagePattern = "Brackets don't match";
        Stack<String> stack = new Stack<>();
        for (String token : tokenizedExpression) {
            if (token.equals("(")) {
                stack.push("(");
            } else if (token.equals(")")) {
                try {
                    stack.pop();
                } catch (EmptyStackException e) {
                    return ValidatorResponse.of(ValidatorResponse.StatusCode.ERROR, errorMessagePattern);
                }
            }
        }
        return (stack.size() == 0 ? ValidatorResponse.of(ValidatorResponse.StatusCode.OKAY, "Successfully") : ValidatorResponse.of(ValidatorResponse.StatusCode.ERROR, errorMessagePattern));
    }
        
    public static ValidatorResponse checkForIllegalToken(String[] tokenizedExpression) {
        final String errorMessagePattern = "Illegal literal in the expression: \"%s\"";
        for (String token : tokenizedExpression) {
            if (!(ExpressionTokenizer.isBracket(token) || ExpressionTokenizer.isNumeric(token) || ExpressionTokenizer.isOperator(token))) {
                return ValidatorResponse.of(ValidatorResponse.StatusCode.ERROR, String.format(errorMessagePattern, token));
            }
        }
        return ValidatorResponse.of(ValidatorResponse.StatusCode.OKAY, "Successfully");
    }

    public static ValidatorResponse checkForBadSequence(String[] tokenizedExpression) {
        final String errorMessagePattern = "Bad sequence in the expression: \"%s\" after \"%s\". ";
        String previousToken = null;
        String errorMessage = null;
        for (String token : tokenizedExpression) {
            if (ExpressionTokenizer.isOperator(token) && ExpressionTokenizer.isOperator(previousToken)) {
                errorMessage = "Operands missed.";
            } else if (ExpressionTokenizer.isNumeric(token) && ExpressionTokenizer.isNumeric(previousToken)) {
                errorMessage = "Operation missed.";
            } else if (token.equals(")") && previousToken != null && previousToken.equals("(")) {
                errorMessage = "Expression in brackets missed.";
            }
            if (errorMessage != null) {
                return ValidatorResponse.of(ValidatorResponse.StatusCode.ERROR, String.format(errorMessagePattern, token, previousToken) + errorMessage);
            }
            previousToken = token;
        }
        return ValidatorResponse.of(ValidatorResponse.StatusCode.OKAY, "Successfully");
    }
}
