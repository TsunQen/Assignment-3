package org.ioopm.calculator.parser;

import java.io.StreamTokenizer;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import org.ioopm.calculator.SyntaxErrorException;
import org.ioopm.calculator.IllegalInputException;
import org.ioopm.calculator.ast.*;

/**
 * Translating String to AST tree of following possible nodes:
 * SymbolicExpression, Addition, Subtraction, Division, Multiplication
 * Sin, Cos, Exp, Log, Negation, Quit, Clear, Vars
 * Constant, NamedConstant, Variable
 */
public class CalculatorParser {
    private StreamTokenizer st = null;
    private String expression = null;
    private static final Set<String> keywords = new HashSet<String>(Arrays.asList(new String[] {"quit","vars","exp","log","sin","cos","clear"}));

    public CalculatorParser() {
    }

    /**
     * @brief translate a string from mathematical expression to an AST tree
     * @param expression the expression to translate
     * @return some subclass of SymbolicExpression if expression has correct formation, else throw exception
     */
    public SymbolicExpression parse(String expression) throws IOException, IllegalInputException {
        this.st = new StreamTokenizer(new StringReader(expression));
        this.st.ordinaryChar('-');
        this.st.ordinaryChar('/');
        this.st.eolIsSignificant(true);
        this.expression = expression;

        if(expression.trim().isEmpty()) {
            throw new IllegalInputException("Empty string");
        }


        SymbolicExpression statement = parseStatement();
        return statement;
    }

    private SymbolicExpression parseStatement() throws IOException, IllegalInputException {
        SymbolicExpression result;
        if(this.st.nextToken() != this.st.TT_NUMBER) {
            if(this.st.ttype == this.st.TT_WORD) { // to allow negation
                if((this.st.sval).equals("quit")) {
                    result = parseQuit();
                } else if((this.st.sval).equals("vars")) {
                    result = parseVars();
                } else if ((this.st.sval).equals("clear")) {
                    result = parseClear();
                } else {
                    this.st.pushBack();
                    result = parseAssignment();
                }
            } else {
                this.st.pushBack();
                result = parseAssignment();
            }
        } else {
            this.st.pushBack();
            result = parseAssignment();
        }
        return result;
    }

    private SymbolicExpression parseQuit() throws IOException {
        SymbolicExpression command = Quit.instance();
        return command;
    }

    private SymbolicExpression parseVars() throws IOException {
        SymbolicExpression command = Vars.instance();
        return command;
    }

    private SymbolicExpression parseClear() throws IOException {
        SymbolicExpression command = Clear.instance();
        return command;
    }

    private SymbolicExpression parseAssignment() throws IOException, IllegalInputException {
        SymbolicExpression result = parseExpression();


        while(this.st.nextToken() != StreamTokenizer.TT_EOF){
            if(this.st.ttype == '=') {
                if(this.st.nextToken() != this.st.TT_WORD) {
                    throw new IllegalInputException("Wrong variable type");
                } else {
                    String var = this.st.sval;
                    Constants ht = new Constants();
                    if(keywords.contains(var)) {
                        throw new IllegalInputException("Variable name " + this.st.sval + " not available ");
                    } else if(ht.namedConstants.get(var) != null) {
                        throw new IllegalInputException("Cannot redefine named constant '"+ var + "'");
                    } else {
                        result = new Assignment(result, new Variable(var));
                    }
                }
            }


        }

        this.st.pushBack();

        return result;
    }

    private SymbolicExpression parseExpression() throws IOException, IllegalInputException {
        SymbolicExpression sum = parseTerm();
        this.st.nextToken();

        while (this.st.ttype == '+' || this.st.ttype == '-') {
            if(this.st.ttype == '+'){
                sum = new Addition(sum, parseTerm());
            } else {
                sum = new Subtraction(sum, parseTerm());
            }
            this.st.nextToken();
        }
        this.st.pushBack();

        return sum;
    }

    private SymbolicExpression parseTerm() throws IOException, IllegalInputException {
        SymbolicExpression result = parseFactor();
        this.st.nextToken();

        while (this.st.ttype == '*' || this.st.ttype == '/') {
            if(this.st.ttype == '*') {
                result = new Multiplication(result, parseFactor());
            } else {
                result = new Division(result, parseFactor());
            }

            this.st.nextToken();
        }
        this.st.pushBack();
        return result;
    }

    private boolean tokenIsNumber() throws IOException {
        if (this.st.ttype == this.st.TT_NUMBER) {
            //System.out.println(this.st.sval);
            return true;
        }
        else {
            return false;
        }
    }

    private boolean tokenIsUnary() {
        if(this.st.ttype == this.st.TT_WORD) {
            if((this.st.sval).equals("exp")) return true;
            if((this.st.sval).equals("log")) return true;
            if((this.st.sval).equals("sin")) return true;
            if((this.st.sval).equals("cos")) return true;
        }
        if(this.st.ttype == '-') return true;

        return false;
    }

    private SymbolicExpression parseFactor() throws IOException, IllegalInputException {
        SymbolicExpression result;
        if (this.st.nextToken() == '('){
            result = parseExpression();

            if (this.st.nextToken() != ')') {
                throw new IllegalInputException("expected ')'");
            }
        } else if(tokenIsNumber()) {
            this.st.pushBack();
            result = parseNumber();
        } else if(tokenIsUnary()) {
            this.st.pushBack();
            result = parseUnary();
        } else {
            this.st.pushBack();
            result = parseIdentifier();
        }
        return result;
    }

    private SymbolicExpression parseNumber() throws IOException {
        if (this.st.nextToken() == this.st.TT_NUMBER) {
            return new Constant(this.st.nval);
        } else {
            throw new SyntaxErrorException("Expected number");
        }
    }

    private SymbolicExpression parseUnary() throws IOException, IllegalInputException {
        this.st.nextToken();
        if(this.st.ttype == this.st.TT_WORD) {

            if((this.st.sval).equals("exp")) {
                return new Exp(parseFactor());

            } else if ((this.st.sval).equals("log")){
                return new Log(parseFactor());

            } else if ((this.st.sval).equals("sin")){
                return new Sin(parseFactor());

            } else {
                return new Cos(parseFactor());
            }

        } else {
            return new Negation(parseFactor());

        }
    }

    private SymbolicExpression parseIdentifier() throws IOException, IllegalInputException {
        if (this.st.nextToken() == this.st.TT_WORD) {

            if(keywords.contains(this.st.sval)) {
                throw new IllegalInputException("Variable name " + this.st.sval + " not available ");
            }

            Constants ht = new Constants();
            if(ht.namedConstants.get(this.st.sval) != null) {
                return new NamedConstant(this.st.sval);
            } else {
                return new Variable(this.st.sval);
            }
        } else {
            throw new IllegalInputException("Invalid syntax");
        }
    }
}
