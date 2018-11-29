package org.ioopm.calculator.ast;
/**
 * Subclass of Atom
 */
public class NamedConstant extends Atom {
    private double value = 1;
    private String name;
    private Constants ht = new Constants();

    /**
     * @brief creates named constant node of name name
     * @param name the named constant's name
     */
    public NamedConstant (String name) {
	this.name = name;
	this.value = ht.namedConstants.get(name);
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
	if (other instanceof NamedConstant) {
	    return this.equals((NamedConstant) other);
	} else {
	    return false;
	}
    }

    public boolean equals(NamedConstant other) {
	return this.value == other.value;
    }

    /**
     * @see SymbolicExpression#eval(Environment vars)
     */
    public SymbolicExpression eval(Environment vars) {	
	return new Constant(this.value);     
    }
}
