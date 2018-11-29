package org.ioopm.calculator.ast;

/**
 * Subclass of Atom
 */
public class Variable extends Atom {
    private String identifier;

    /**
     * @brief creates variable node of identifier
     * @param identifier the variable's identifier
     */
    public Variable(String identifier) {
        this.identifier = identifier;
    }

    /**
     * @brief prints the constant node
     */
    public String toString() {
        return this.identifier;
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
        if (other instanceof Variable) {
            return this.equals((Variable) other);
        } else {
            return false;
        }
    }

    public boolean equals(Variable other) {
        return this.identifier.equals(other.identifier);
    }

    /**
     * @see SymbolicExpression#eval(Environment vars)
     */
    public SymbolicExpression eval(Environment vars) {
        if(vars.get(new Variable(this.identifier)) != null) {
            return vars.get(new Variable(this.identifier));
        } else {
            return new Variable(this.identifier);
        }
    }

    /**
     * @brief returns the hashcode of its identifier
     * @return the hashcode of its identifier
     */
    public int hashCode() {
        return this.identifier.hashCode();
    }
}
