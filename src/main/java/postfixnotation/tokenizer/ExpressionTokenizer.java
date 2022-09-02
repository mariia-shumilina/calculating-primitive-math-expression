package postfixnotation.tokenizer;

import java.util.Arrays;
import java.util.Set;
import java.util.regex.Pattern;


   
public class ExpressionTokenizer {
       
    private static final String regexSplit = "(?<=[-+*/^() ])|(?=[-+*/^() ])";
       
    private static final Pattern numberPattern = Pattern.compile("-?\\d+(\\.\\d+)?");
       
    private static final Set<String> operators = Set.of("+", "-", "/", "*", "^");
       
    private static final Set<String> brackets = Set.of("(", ")");

    public static boolean isNumeric(String number) {
        if (number == null) {
            return false;
        }
        return numberPattern.matcher(number).matches();
    }

    public static boolean isOperator(String operation){
        if (operation == null) {
            return false;
        }
        return operators.contains(operation);
    }

    public static boolean isBracket(String bracket){
        if (bracket == null) {
            return false;
        }
        return brackets.contains(bracket);
    }
       
    public static String[] tokenizeExpression(String expression) {
        if(expression == null){
            throw new IllegalArgumentException("Expression is null");
        }
        return Arrays.stream(expression.replaceAll("\\s+", " ").split(regexSplit))
                .filter(x -> !x.equals(" "))
                .toArray(String[]::new);
    }
}
