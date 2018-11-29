package org.ioopm.calculator;

import java.io.IOException;
import org.ioopm.calculator.*;
import org.ioopm.calculator.parser.*;
import org.ioopm.calculator.ast.*;
import java.util.Scanner;

class Calculator {
    public static void printStatistics(int totalEntered, int successEval, int fullyEval)
    {
        System.out.println("\nStatistics ");
        System.out.println("* Total expression entered: " + totalEntered);
        System.out.println("* Succesfully evaluated expressions: " + successEval);
        System.out.println("* Fully evaulated expressions: " + fullyEval);
    }

    public static void main(String[] args) {
        final CalculatorParser parser = new CalculatorParser();
        Environment vars = new Environment();
        Environment vars_backup = new Environment();
        int fullyEval = 0;
        int successEval = 0;
        int totalEntered = 0;

        int quit = 0;
        while(quit == 0)
            try {
                vars_backup = vars;
                System.out.print("Enter an expression: ");
                String input = System.console().readLine();
                totalEntered++;
                SymbolicExpression answer = parser.parse(input);
                if(answer.isCommand()) {
                    totalEntered--;
                    if(answer == Quit.instance()) {
                        quit = 1;
                        printStatistics(totalEntered, successEval, fullyEval);
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
                    successEval++;
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
                vars = vars_backup;
                continue;
            }

    }
}
