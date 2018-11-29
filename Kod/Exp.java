package org.ioopm.calculator.ast;

/**
 * Subclass of Unary
 */
public class Exp extends Unary {
    private SymbolicExpression subtree = null;

    /**
     * @brief creates exp node of a subtree
     * @param subtree the expression to operate exp on
     */
    public Exp(SymbolicExpression subtree) {
	super(subtree);
	this.subtree = subtree;
    }

    /**
     * @see SymbolicExpression#getName()
     */
    public String getName() {
	return "exp ";
    }

    /**
     * @see SymbolicExpression#eval(Environment vars)
     */
    public SymbolicExpression eval(Environment vars) {
	SymbolicExpression subtree = this.subtree.eval(vars);
	if (subtree.isConstant()) {
	    return new Constant(Math.exp(subtree.getValue()));
	} else {
	    return new Exp(subtree);
	}
    }
}
