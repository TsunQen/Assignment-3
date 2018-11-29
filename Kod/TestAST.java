package org.ioopm.calculator;

import java.io.IOException;
import org.ioopm.calculator.SyntaxErrorException;
import org.ioopm.calculator.parser.*;
import org.ioopm.calculator.ast.*;
import java.util.Scanner;
import java.util.Set;

class TestAST {
    public static void testPrinting(String expected, SymbolicExpression e) {
	if (expected.equals("" + e)) {
	    System.out.println("Passed: " + e);
	} else {
	    System.out.println("Error: expected '" + expected + "' but got '" + e + "'");
	}
    }

    public static void testEvaluating(SymbolicExpression expected, SymbolicExpression e) {
	Environment ht = new Environment();
	Assignment ass = new Assignment(new Constant(8), new Variable("x"));
	ass.eval(ht);
	Set keyset = ht.keySet();
	//System.out.println("Key set values are: " + keyset);
      
	SymbolicExpression r = e.eval(ht);
	if (r.equals(expected)) {
	    System.out.println("Passed: " + r);
	} else {
	    System.out.println("Error: expected '" + expected + "' but got '" + r + "'");
	}
    }
    
    public static void main(String[] args) {
	Constant c1 = new Constant(5);
	Constant c2 = new Constant(2);
	Variable v = new Variable("x");
	Addition a1 = new Addition(c1, v);
	Addition a2 = new Addition(c1, a1);
	Multiplication m1 = new Multiplication(a1, c2);
	Multiplication m2 = new Multiplication(c2, a1);

	Negation n1 = new Negation(c1);
	Subtraction s1 = new Subtraction(c1, c2);
	Subtraction s7 = new Subtraction(c1, n1);
	Division d1 = new Division(a1, c1);
	Sin sin1 = new Sin(c1);
	Exp exp = new Exp(new Constant(1));
	Cos cos = new Cos(new Constant(1));
	Log log = new Log(new Constant(1));
	
	testPrinting("(5.0 + x) * 2.0", m1);
	testPrinting("2.0 * (5.0 + x)", m2);
	testPrinting("5.0 - 2.0", s1);
	testPrinting("5.0 + 5.0 + x", a2);
	testPrinting("sin 5.0", sin1);
	testPrinting("cos 1.0", cos);
	testPrinting("5.0 - -(5.0)", s7);
	testPrinting("(5.0 + x) / 5.0", d1);
	testPrinting("exp 1.0", exp);
	testPrinting("log 1.0", log);

	Subtraction s3 = new Subtraction(c1, c2);
	Subtraction s4 = new Subtraction(c2, new Constant(1));
	Subtraction s5 = new Subtraction(s3, s4);
	
	SymbolicExpression a = new Addition(new Constant(5), new Constant(37));
	SymbolicExpression b = new Constant(42);

	Addition a3 = new Addition(v, v);

	testEvaluating(b, a);
	testEvaluating(new Constant(2.718281828459045), exp);
	testEvaluating(new Constant(0.5403023058681398), cos);
	testEvaluating(new Constant(-0.9589242746631385), sin1);
	testEvaluating(new Constant(0), log);
	testEvaluating(new Constant(-5), n1);
	testEvaluating(new Constant(2), s5);
	testEvaluating(new Constant(26), m1);
	testEvaluating(new Constant(16), a3);

	NamedConstant pi = new NamedConstant("pi");
	Subtraction s6 = new Subtraction(pi, pi);
	testEvaluating(new Constant(0), s6);

	
	Assignment error = new Assignment(s4, pi);
	try {
	testEvaluating(new Constant(0), error);
	} catch(IllegalExpressionException e) {
	    System.out.print("Illegal command: ");
	    System.out.println(e.getMessage());
	}

	
    }
}
