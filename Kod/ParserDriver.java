package org.ioopm.calculator;

import java.io.IOException;
import org.ioopm.calculator.SyntaxErrorException;
import org.ioopm.calculator.parser.*;
import org.ioopm.calculator.ast.*;
import java.util.Scanner;

class ParserDriver {
    private void termT() {
        System.out.println("test");
    }
    public static void main(String[] args) {
        CalculatorParser p = new CalculatorParser();

        System.out.println("Welcome to the parser!");

        try {
            while(true) {
                System.out.print("Please enter an expression: ");
                Scanner input = new Scanner(System.in);
                String inputStr = input.nextLine();

                SymbolicExpression result = p.parse(inputStr);
                System.out.println("result: " + result);
            }
        } catch(SyntaxErrorException e) {
            System.out.print("Syntax Error: ");
            System.out.println(e.getMessage());
        } catch(IOException e) {
            System.err.println("IO Exception!");
        } catch(IllegalInputException e) {
            System.err.println("IO Exception!");
        }

    }
}
