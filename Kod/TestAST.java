package org.ioopm.calculator;

import org.ioopm.calculator.ast.*;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestAST{

    @Test
    public void testEval() {
        Environment vars = new Environment();
        Constant c = new Constant(1.0);
        Addition a = new Addition(c, c);

        SymbolicExpression evaluated = a.eval(vars);
        double result = ((Constant)evaluated).getValue();
        assertEquals(2.0, result, 0);
    }

    @Test
    public void testEval2(){
        Environment vars = new Environment();
        Constant c = new Constant(3.0);
        Addition a = new Addition(c, c);
        Multiplication m = new Multiplication(a, c);

        SymbolicExpression evaluated = m.eval(vars);
        double result = ((Constant)evaluated).getValue();
        assertEquals(18.0, result, 0);
    }

    @Test
    public void testEval3(){
        Environment vars = new Environment();
        Constant c = new Constant(3.0);
        Sin s = new Sin(c);
        Negation n = new Negation(s);
        Addition a = new Addition(s, n);

        SymbolicExpression evaluated = a.eval(vars);
        double result = ((Constant)evaluated).getValue();
        assertEquals(0.0, result, 0);
    }

    @Test
    public void testEval4(){
        Environment vars = new Environment();
        Constant c = new Constant(Math.PI);
        Constant c1 = new Constant(2.0);
        Multiplication m = new Multiplication(c1, c);
        Subtraction s = new Subtraction(m, c);
        Cos cos = new Cos(s);

        SymbolicExpression evaluated = cos.eval(vars);
        double result = ((Constant)evaluated).getValue();
        assertEquals(-1.0, result, 0);
    }

    @Test
    public void testEval5(){
        Environment vars = new Environment();
        Constant c = new Constant(0.0);
        Constant c2 = new Constant(2.0);
        Exp e = new Exp(c);
        Division d = new Division(e, c2);

        SymbolicExpression evaluated = d.eval(vars);
        double result = ((Constant)evaluated).getValue();
        assertEquals(0.5, result, 0);
    }

    @Test
    public void testGetValue(){
        Constant c0 = new Constant(3.0);
        double result = c0.getValue();
        assertEquals(3.0, result, 0);
    }

    @Test
    public void testGetValue2(){
        Constant c0 = new Constant(0.0);
        double result = c0.getValue();
        assertEquals(0.0, result, 0);
    }

    @Test
    public void testIsConstant(){
        Constant exp = new Constant(2.0);
        assertTrue(exp.isConstant());
    }

    @Test
    public void testIsConstant2(){
        Variable v = new Variable("x");
        assertFalse(v.isConstant());
    }

    @Test
    public void testGetName(){
        Constant c0 = new Constant(1.0);
        Addition a = new Addition(c0, c0);
        assertEquals("+", a.getName());
    }

    @Test
    public void testGetName2(){
        Constant c0 = new Constant(1.0);
        Log l = new Log(c0);
        assertEquals("log", l.getName());
    }

    @Test
    public void testGetName3(){
        Constant c0 = new Constant(5.0);
        Constant c1 = new Constant(3.0);
        Multiplication m = new Multiplication(c0, c1);
        assertEquals("*", m.getName());
    }

    @Test
    public void testIsCommand(){
        assertTrue(Vars.instance().isCommand());
    }

    @Test
    public void testIsCommand2(){
        Variable v = new Variable("x");
        assertFalse(v.isCommand());
    }

    @Test
    public void testGetPriority(){
        Constant c0 = new Constant(1.0);
        Addition a = new Addition(c0, c0);
        assertEquals(80, a.getPriority());
    }

    @Test
    public void testGetPriority2(){
        Constant c0 = new Constant(2.0);
        Log l = new Log(c0);
        assertEquals(40, l.getPriority());
    }

    @Test
    public void testToString(){
        Constant c0 = new Constant(1.0);
        Addition a = new Addition(c0, c0);
        String str = a.toString();
        assertEquals("1.0 + 1.0", str);
    }

    @Test
    public void testToString2(){
        Constant c0 = new Constant(1.0);
        Constant c1 = new Constant(5.0);
        Subtraction s = new Subtraction(c1, c0);
        Multiplication m = new Multiplication(s, c1);
        String str = m.toString();
        assertEquals("(5.0 - 1.0) * 5.0", str);
    }

    @Test
    public void testToString3(){
        Constant c0 = new Constant(2.0);
        Constant c1 = new Constant(6.0);
        Division d = new Division(c1, c0);
        Sin s = new Sin(d);
        Exp e = new Exp(s);
        String str = e.toString();
        assertEquals("exp(sin(6.0 / 2.0))", str);
    }

    @Test
    public void testToString4(){
        Constant c0 = new Constant(1.0);
        Constant c1 = new Constant(5.0);
        Cos c = new Cos(c0);
        Log l = new Log(c);
        Negation n = new Negation(l);
        Variable v = new Variable("x");
        Assignment a = new Assignment(n, v);

        String str = a.toString();
        assertEquals("(-(log(cos(1.0)))) = x", str);
    }

    @Test
    public void testEquals(){
        Constant c0 = new Constant(1.0);
        Variable v = new Variable("x");
        Assignment a = new Assignment(c0, v);
        Assignment a2 = new Assignment(c0, v);

        boolean res = a.equals(a2);
        assertEquals(true, res);
    }

    @Test
    public void testEquals2(){
        Constant c0 = new Constant(1.0);
        Constant c1 = new Constant(3.0);
        Subtraction s = new Subtraction(c1, c0);
        Division d = new Division(c1, s);
        Division d2 = new Division(c1, s);

        boolean res = d.equals(d2);
        assertEquals(true, res);
    }

    @Test
    public void testEquals3(){
        Constant c0 = new Constant(1.0);
        Constant c1 = new Constant(3.0);
        Multiplication m = new Multiplication(c1, c0);
        Multiplication m2 = new Multiplication(c1, c0);
        Negation n = new Negation(m);
        Negation n2 = new Negation(m2);

        boolean res = n.equals(n2);
        assertEquals(true, res);
    }

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestAST.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
    }
}






















/*import java.io.IOException;
import org.ioopm.calculator.SyntaxErrorException;
import org.ioopm.calculator.parser.*;
import org.ioopm.calculator.ast.*;
import java.util.Scanner;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

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

   @Test
   public void testAddition()
    {

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
    }*/
