package org.ioopm.calculator.ast;

import org.ioopm.calculator.*;
/**
 * Subclass of Binary
 */
public class Assignment extends Binary {
    private SymbolicExpression lhs = null;
    private SymbolicExpression rhs = null;

    /**
     * @brief creates assignment node of two subtrees
     * @param lhs the expression to be assigned
     * @param rhs the variable being assigned to
     */
    public Assignment(SymbolicExpression lhs, SymbolicExpression rhs) {
	super(lhs, rhs);
	this.lhs = lhs;
	this.rhs = rhs;
	
    }

    /**
     * @see SymbolicExpression#getName()
     */
    public String getName() {
	return "=";
    }

    /**
     * @brief prints the assignment node
     */
    public String toString() {
	return this.lhs + "";
    }


    /**
     * @see SymbolicExpression#eval(Environment vars)
     */
    public SymbolicExpression eval(Environment vars) {
	SymbolicExpression lhs_result = this.lhs.eval(vars);
	SymbolicExpression rhs_result = this.rhs;
	if(rhs_result.isConstant()) {
	    throw new IllegalExpressionException("Error: cannot redefine named constant");
	}

	Variable rhs_var = new Variable(rhs_result.toString());
	vars.put(rhs_var, lhs_result);

	if(lhs_result.isConstant()) {
	    return new Constant(lhs_result.getValue());
	} else {
	return new Assignment(lhs_result, rhs_result);
	}
    }
}
