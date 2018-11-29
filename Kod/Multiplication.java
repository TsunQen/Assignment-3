package org.ioopm.calculator.ast;
/**
 * @see SymbolicExpression#eval(Environment vars)
 */
public class Multiplication extends Binary {
    private SymbolicExpression lhs = null;
    private SymbolicExpression rhs = null;

    /**
     * @brief creates multiplication node of two subtrees
     * @param lhs the left subtree to multiplicate
     * @param rhs the right subtree to multiplicate
     */
    public Multiplication(SymbolicExpression lhs, SymbolicExpression rhs) {
        super(lhs, rhs);
        this.lhs = lhs;
        this.rhs = rhs;

    }

    /**
     * @see SymbolicExpression#getName()
     */
    public String getName() {
        return "*";
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
            return new Constant((lhs_result.getValue()) * (rhs_result.getValue()));
        } else {
            return new Multiplication(lhs_result, rhs_result);
        }
    }
}
