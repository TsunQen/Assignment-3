package org.ioopm.calculator.ast;

import org.ioopm.calculator.IllegalExpressionException;

/**
 * Subclass of SymbolicExpression
 * Superclass of all commands (Vars, Quit, Clear)
 */
public abstract class Command extends SymbolicExpression {

    public Command() {
    }

    /**
     * @see SymbolicExpression#eval(Environment vars)
     */
    @Override
    public SymbolicExpression eval(Environment vars) throws IllegalExpressionException {
	throw new IllegalExpressionException("Commands may not be evaluated");
    }

    /**
     * @see SymbolicExpression#isCommand()
     */
    public boolean isCommand() {
	return true;
    }
}
