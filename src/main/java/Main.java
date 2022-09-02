import postfixnotation.calculator.ExpressionCalculator;
import postfixnotation.calculator.ExpressionCalculatorImp;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        ExpressionCalculator calculator = new ExpressionCalculatorImp();
        System.out.println(calculator.calculateExpression("8+7/(-10)-95/4"));
    }
}
