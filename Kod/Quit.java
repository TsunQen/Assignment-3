package org.ioopm.calculator.ast;
/**
 * Subclass of Command
 */
public class Quit extends Command {

    private static final Quit theInstance = new Quit();
    private Quit() {}

    /**
     * @brief creates maximum one instance of Quit
     */
    public static Quit instance() {
        return theInstance;
    }
}
