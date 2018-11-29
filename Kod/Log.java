package org.ioopm.calculator.ast;

/**
 * Subclass of Unary
 */
public class Log extends Unary {
    private SymbolicExpression subtree = null;

    /**
     * @brief creates log node of a subtree
     * @param subtree the expression to operate log on
     */
    public Log(SymbolicExpression subtree) {
	super(subtree);
	this.subtree = subtree;
    }

    /**
     * @see SymbolicExpression#getName()
     */
    public String getName() {
	return "log ";
    }

    /**
     * @see SymbolicExpression#eval(Environment vars)
     */
    public SymbolicExpression eval(Environment vars) {
	SymbolicExpression subtree = this.subtree.eval(vars);
	if (subtree.isConstant()) {
	    return new Constant(Math.log(subtree.getValue()));
	} else {
	    return new Log(subtree);
	}
    }
}
