package org.ioopm.calculator.ast;
/**
 * Subclass of Command
 */
public class Clear extends Command {

    private static final Clear theInstance = new Clear();
    private Clear() {}

    /**
     * @brief creates maximum one instance of Clear
     */
    public static Clear instance() {
        return theInstance;
    }
}
