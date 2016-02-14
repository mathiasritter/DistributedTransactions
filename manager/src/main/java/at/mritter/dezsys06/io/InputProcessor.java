package at.mritter.dezsys06.io;

/**
 * Processes the user input
 *
 * @author Mathias Ritter
 * @version 1.0
 */
public interface InputProcessor {

    /**
     * Process the user input
     *
     * @param input The user input as a string
     */
    public void processInput(String input);

    /**
     * Exit the application
     */
    public void exitApplication();

}
