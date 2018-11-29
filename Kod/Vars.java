package org.ioopm.calculator.ast;
/**
 * Subclass of Command
 */
public class Vars extends Command {

    private static final Vars theInstance = new Vars();
    private Vars() {}

    /**
     * @brief creates maximum one instance of Vars
     */
    public static Vars instance() {
        return theInstance;
    }
    
   
}
