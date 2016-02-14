package at.mritter.dezsys06;

import org.apache.commons.cli.*;

/**
 * This class is used to parse the command line arguments
 *
 * @author Mathias Ritter
 */
public class CommandParser {

    public static CommandLine parse(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        Options options = getOptions();
        return parser.parse(options, args);
    }

    /**
     * Builds the command line options
     *
     * @return the command line options
     */
    private static Options getOptions() {

        Options options = new Options();


        Option port = Option.builder("P").argName("port").longOpt("port")
                .desc("The port where the server is going to be created. Default: 35768").hasArg().build();

        options.addOption(port);

        return options;
    }


    /**
     * Creates a Unix like help page that will be shown if the user enters invalid parameters
     */
    public static void printHelp() {
        String header = "Transaction manager that sends sql queries to stations.";
        String footer = "";

        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("dezsys06_manager", header, getOptions(), footer, true);
    }

}
