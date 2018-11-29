package org.ioopm.calculator.ast;

/**
 * Subclass of Unary
 */
public class Cos extends Unary {
    private SymbolicExpression subtree = null;

    /**
     * @brief creates cos node of a subtree
     * @param subtree the expression to operate cos on
     */
    public Cos(SymbolicExpression subtree) {
	super(subtree);
	this.subtree = subtree;
    }

    /**
     * @see SymbolicExpression#getName()
     */
    public String getName() {
	return "cos ";
    }

    /**
     * @see SymbolicExpression#eval(Environment vars)
     */
    public SymbolicExpression eval(Environment vars) {
	SymbolicExpression subtree = this.subtree.eval(vars);
	if (subtree.isConstant()) {
	    return new Constant(Math.cos(subtree.getValue()));
	} else {
	    return new Cos(subtree);
	}
    }
}
