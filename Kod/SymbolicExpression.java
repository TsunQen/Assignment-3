package org.ioopm.calculator.ast; 
/**
 * Root class of all operators, functions and atoms
*/
public abstract class SymbolicExpression {

    
    public SymbolicExpression() {
   
    }

    /**
     * @brief checks if this object is constant
     * @return true if this object is constant, else false
    */
    public boolean isConstant() {
	return false;
    }

    /**
     * @brief checks if this object is command
     * @return true if this object is command, else false
    */
    public boolean isCommand() {
	return false;
    }

    /**
     * @brief returns the name of operator objects
     * @return the name of the object if of any operator class, else throw exception
    */
    public String getName() {
	throw new RuntimeException("getName() called on expression with no operator");
    }

    /**
     * @brief checks the priority of object
     * @return the priority number
    */
    public int getPriority() {
	return 50;
    }

    /**
     * @brief returns value of constants
     * @return the value of the object if of constant class, else throw exception
    */
    public double getValue() {
	throw new RuntimeException("getValue() called on non-constant");
    }

    /**
     * @brief evaluates the expression
     * @return the value of the expression, as long as possible
    */
    public abstract SymbolicExpression eval(Environment vars);
}
