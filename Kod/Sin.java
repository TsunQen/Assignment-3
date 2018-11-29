package org.ioopm.calculator.ast;

/**
 * Subclass of Unary
 */
public class Sin extends Unary {
    private SymbolicExpression subtree = null;

    /**
     * @brief creates sin node of a subtree
     * @param subtree the expression to operate sin on
     */
    public Sin(SymbolicExpression subtree) {
	super(subtree);
	this.subtree = subtree;
    }

    /**
     * @see SymbolicExpression#getName()
     */
    public String getName() {
	return "sin ";
    }

    /**
     * @see SymbolicExpression#eval(Environment vars)
     */
    public SymbolicExpression eval(Environment vars) {
	SymbolicExpression subtree = this.subtree.eval(vars);
	if (subtree.isConstant()) {
	    return new Constant(Math.sin(subtree.getValue()));
	} else {
	    return new Sin(subtree);
	}
    }
}
