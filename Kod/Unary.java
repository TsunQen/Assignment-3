package org.ioopm.calculator.ast;
/**
 * Subclass of SymbolicExpression
 * Superclass of all unary operations (Sin, Cos, Exp, Log, Negation)
 */
public abstract class Unary extends SymbolicExpression {
    private SymbolicExpression subtree = null;

    /**
     * @brief creates unary node with a subtree
     * @param subtree the subtree to be operated
     */
    public Unary(SymbolicExpression subtree) {
        this.subtree = subtree;
    }

    /**
     * @brief prints the node
     */
    public String toString() {
        return this.getName() + "" + this.subtree.toString();
    }

    /**
     * @brief checks if other.lhs and other.rhs equal with this.lhs and this.rhs
     * @param other object to be compared
     * @return true if objects are equal, else false
     */
    public boolean equals(Object other) {
        if (other instanceof Unary) {
            return this.equals((Unary) other);
        } else {
            return false;
        }
    }

    public boolean equals(Unary other) {
        return this.subtree.equals(other.subtree);
    }
}
