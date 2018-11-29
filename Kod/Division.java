package org.ioopm.calculator.ast;
/**
 * Subclass of Binary
 */
public class Division extends Binary {
    private SymbolicExpression lhs = null;
    private SymbolicExpression rhs = null;

    /**
     * @brief creates division node of two subtrees
     * @param lhs the numerator of the division operation
     * @param rhs the denominator of the division operation
     */
    public Division(SymbolicExpression lhs, SymbolicExpression rhs) {
	super(lhs, rhs);
	this.lhs = lhs;
	this.rhs = rhs;
	
    }

    /**
     * @see SymbolicExpression#getName()
     */
    public String getName() {
	return "/";
    }

    /**
     * @see SymbolicExpression#getPriority()
     */
    public int getPriority() {
	return 100;
    }

    /**
     * @see SymbolicExpression#eval(Environment vars)
     */
    public SymbolicExpression eval(Environment vars) {
	SymbolicExpression lhs_result = this.lhs.eval(vars);
	SymbolicExpression rhs_result = this.rhs.eval(vars);
	
	if (lhs_result.isConstant() && rhs_result.isConstant()) {
	    return new Constant((lhs_result.getValue()) / (rhs_result.getValue()));
	} else {
	    return new Division(lhs_result, rhs_result);
	}
    }
}
