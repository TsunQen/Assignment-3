package org.ioopm.calculator.ast;

/**
 * Subclass of Unary
 */
public class Negation extends Unary {
    private SymbolicExpression subtree = null;

    /**
     * @brief creates negation node of a subtree
     * @param subtree the expression to negate
     */
    public Negation(SymbolicExpression subtree) {
	super(subtree);
	this.subtree = subtree;
    }

    /**
     * @see SymbolicExpression#getName()
     */
    public String getName() {
	return "-";
    }

    /**
     * @brief returns the string representation of the node
     */
    public String toString() {
	return "-(" + this.subtree.toString() + ")";
    }

    /**
     * @see SymbolicExpression#eval(Environment vars)
     */
    public SymbolicExpression eval(Environment vars) {
	SymbolicExpression subtree = this.subtree.eval(vars);
	if (subtree.isConstant()) {
	    return new Constant((subtree.getValue()) * -1);
	} else {
	    return new Negation(subtree);
	}
    }
}
