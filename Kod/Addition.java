package org.ioopm.calculator.ast;
/**
 * Subclass of Binary
 */
public class Addition extends Binary {
    private SymbolicExpression lhs = null;
    private SymbolicExpression rhs = null;

    /**
     * @brief creates addition node of two subtrees
     * @param lhs the left subtree to be added
     * @param rhs the right subtree to be added
     */
    public Addition(SymbolicExpression lhs, SymbolicExpression rhs) {
	super(lhs, rhs);
	this.lhs = lhs;
	this.rhs = rhs;	
    }

    /**
     * @see SymbolicExpression#getName()
     */
    public String getName() {
	return "+";
    }

    /**
     * @see SymbolicExpression#eval(Environment vars)
     */
    public SymbolicExpression eval(Environment vars) {
	SymbolicExpression lhs_result = this.lhs.eval(vars);
	SymbolicExpression rhs_result = this.rhs.eval(vars);
	
	if (lhs_result.isConstant() && rhs_result.isConstant()) {
	    return new Constant((lhs_result.getValue()) + (rhs_result.getValue()));
	} else {
	    return new Addition(lhs_result, rhs_result);
	}
    }

}
