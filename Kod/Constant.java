package org.ioopm.calculator.ast;
/**
 * Subclass of Atom
 */
public class Constant extends Atom {
    private double value;

    /**
     * @brief creates constant node of value value
     * @param value the constant's value
     */
    public Constant(double value) {
	this.value = value;
    }

    /**
     * @see SymbolicExpression#isConstant()
     */
    public boolean isConstant() {
	return true;
    }

    /**
     * @see SymbolicExpression#getValue()
     */
    public double getValue() {
	return this.value;
    }

    /**
     * @brief prints the constant node
     */
    public String toString() {
	return String.valueOf(this.value);
    }

    /**
     * @see SymbolicExpression#getPriority()
     */
    public int getPriority() {
	return 101;
    }

    /**
     * @brief checks if other.lhs and other.rhs equal with this.lhs and this.rhs
     * @param other object to be compared
     * @return true if objects are equal, else false
     */
    public boolean equals(Object other) {
	if (other instanceof Constant) {
	    return this.equals((Constant) other);
	} else {
	    return false;
	}
    }

    public boolean equals(Constant other) {
	return this.value == other.value;
    }

    /**
     * @see SymbolicExpression#eval(Environment vars)
     */
    public SymbolicExpression eval(Environment vars) {
	
	return new Constant(this.value);
	
    }
}
