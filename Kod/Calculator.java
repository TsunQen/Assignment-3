package org.ioopm.calculator;

import java.io.IOException;
import org.ioopm.calculator.*;
import org.ioopm.calculator.parser.*;
import org.ioopm.calculator.ast.*;
import java.util.Scanner;

class Calculator {
    public static void main(String[] args) {
        final CalculatorParser parser = new CalculatorParser();
        Environment vars = new Environment();
        Environment vars_bup = new Environment();
        int fullyEval = 0;
        int succesEval = 0;
        int totalEntered = 0;

        int quit = 0;
        while(quit == 0)
            try {
                vars_bup = vars;
                System.out.print("Enter an expression: ");
                String input = System.console().readLine();
                totalEntered++;
                SymbolicExpression answer = parser.parse(input);
                if(answer.isCommand()) {
                    totalEntered--;
                    if(answer == Quit.instance()) {
                        quit = 1;
                        System.out.println("\nStatistics ");
                        System.out.println("* Total expression entered: " + totalEntered);
                        System.out.println("* Succesfully evaluated expressions: " + succesEval);
                        System.out.println("* Fully evaulated expressions: " + fullyEval);
                    } else if(answer == Vars.instance()) {
                        for(Variable var : vars.keySet()) {
                            System.out.println(var + " = " + vars.get(var));
                        }
                    } else {
                        vars.clear();
                    }
                } else {
                    SymbolicExpression result = answer.eval(vars);
                    System.out.println(result);
                    succesEval++;
                    if(result instanceof Constant) {
                        fullyEval++;
                    }
                }

            } catch(IOException e) {
                System.err.println("IO Exception!");
            }
            catch(IllegalInputException e) {
                System.out.print("Illegal input: ");
                System.out.println(e.getMessage());
                continue;
            }
            catch(IllegalExpressionException e) {
                System.out.print("Illegal Expression: ");
                System.out.println(e.getMessage());
                vars = vars_bup;
                continue;
            }

    }
}
