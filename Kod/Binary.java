package org.ioopm.calculator.ast;
/**
 * Subclass of SymbolicExpression
 * Superclass of all binary operations (Addition, Subtraction, Multiplication, Division, Assignment)
 */
public abstract class Binary extends SymbolicExpression {
    private SymbolicExpression lhs = null;
    private SymbolicExpression rhs = null;

    /**
     * @brief creates binary node with two subtrees
     * @param lhs the left subtree to be operated 
     * @param rhs the right subtree to be operated
     */
    public Binary(SymbolicExpression lhs, SymbolicExpression rhs) {
	this.lhs = lhs;
	this.rhs = rhs;
    }

    /**
     * @brief prints the node
     */
    public String toString() {
	if(this.lhs.getPriority() < this.getPriority()) {
	    return "(" + this.lhs + ") " + this.getName() + " " + this.rhs;
	} else if(this.rhs.getPriority() < this.getPriority()) {
	    return this.lhs + " " + this.getName() + " (" + this.rhs + ")";
	} else {
	    return this.lhs + " " + this.getName() + " " + this.rhs;
	}
    }

    /**
     * @brief checks if other.lhs and other.rhs equal with this.lhs and this.rhs
     * @param other object to be compared
     * @return true if objects are equal, else false
     */
    public boolean equals(Object other) {
	if (other instanceof Binary) {
	    return this.equals((Binary) other);
	} else {
	    return false;
	}
    }

    public boolean equals(Binary other) {
	return (this.lhs.equals(other.lhs) && this.rhs.equals(other.rhs));
    }
}
