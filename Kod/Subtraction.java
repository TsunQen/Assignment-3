package org.ioopm.calculator.ast;
/**
 * Subclass of Binary
 */
public class Subtraction extends Binary {
    private SymbolicExpression lhs = null;
    private SymbolicExpression rhs = null;

    /**
     * @brief creates subtraction node of two subtrees
     * @param lhs the left subtree to be subtracted of rhs
     * @param rhs the right subtree to subtract lhs
     */
    public Subtraction(SymbolicExpression lhs, SymbolicExpression rhs) {
	super(lhs, rhs);
	this.lhs = lhs;
	this.rhs = rhs;
	
    }

    /**
     * @see SymbolicExpression#getName()
     */
    public String getName() {
	return "-";
    }

    /**
     * @brief prints the subtraction node
     */
    public String toString() {
	if(rhs instanceof Binary) {
	    return "" + lhs + " - (" + rhs + ")";
	}
	
	return "" + lhs + " - " + rhs + "";
    }
   

   
    /**
     * @see SymbolicExpression#eval(Environment vars)
     */
    public SymbolicExpression eval(Environment vars) {
	SymbolicExpression lhs_result = this.lhs.eval(vars);
	SymbolicExpression rhs_result = this.rhs.eval(vars);
	
	if (lhs_result.isConstant() && rhs_result.isConstant()) {
	    return new Constant((lhs_result.getValue()) - (rhs_result.getValue()));
	} else {	   
	    return new Subtraction(lhs_result, rhs_result);
	}
    }
}
