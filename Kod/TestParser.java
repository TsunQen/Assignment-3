package org.ioopm.calculator;

import java.io.IOException;
import org.ioopm.calculator.*;
import org.ioopm.calculator.parser.*;
import org.ioopm.calculator.ast.*;

class TestParser {
    public static void testPrinting(String expected, SymbolicExpression e) {
        if (expected.equals("" + e)) {
            System.out.println("Passed: " + e);
        } else {
            System.out.println("Error: expected '" + expected + "' but got '" + e + "'");
        }
    }

    public static void tryIllegal(String str) {
        try {
            final CalculatorParser parser = new CalculatorParser();
            SymbolicExpression answer = parser.parse("1 + 1");

            answer = parser.parse(str);
            testPrinting("Illegal input", answer);



        } catch(IOException e) {
            System.err.println("IO Exception!");
        }
        catch(IllegalInputException e) {
            System.out.print("Illegal input: ");
            System.out.println(e.getMessage());
        }
        catch(IllegalExpressionException e) {
            System.out.print("Illegal Expression: ");
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        final CalculatorParser parser = new CalculatorParser();
        Environment vars = new Environment();

        try {
            SymbolicExpression answer = parser.parse("1 + 1");
            testPrinting("1.0 + 1.0", answer);

            answer = parser.parse("5*(4-1) = x");
            testPrinting("5.0 * (4.0 - 1.0)", answer);

            answer = parser.parse("5+5+(5+5)/2");
            testPrinting("5.0 + 5.0 + (5.0 + 5.0) / 2.0", answer);

        } catch(IOException e) {
            System.err.println("IO Exception!");
        }
        catch(IllegalInputException e) {
            System.out.print("Illegal input: ");
            System.out.println(e.getMessage());
        }
        catch(IllegalExpressionException e) {
            System.out.print("Illegal Expression: ");
            System.out.println(e.getMessage());
        }

        tryIllegal("5 ++ 4");
        tryIllegal("5 + quit");
        tryIllegal("x = 1");
        tryIllegal(" ");
        tryIllegal("5 = sin");
        tryIllegal("5 + (5+ 2");
    }
}
