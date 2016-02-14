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


        Option host = Option.builder("h").argName("host").longOpt("host")
                .desc("The ip of the transaction manager. Default: 127.0.0.1").hasArg().build();

        Option port = Option.builder("P").argName("port").longOpt("port")
                .desc("The port of the transaction manager. Default: 35768").hasArg().build();


        Option user = Option.builder("u").argName("user").longOpt("user")
                .desc("The username of the database management system. Default: Current OS user").hasArg().build();

        Option password = Option.builder("p").argName("password").longOpt("password")
                .desc("The password of the database management system. Default: none").hasArg().build();

        Option database = Option.builder("d").argName("database").longOpt("database")
                .desc("The name of the database").required().hasArg().build();


        options.addOption(host);
        options.addOption(port);
        options.addOption(user);
        options.addOption(password);
        options.addOption(database);

        return options;
    }


    /**
     * Creates a Unix like help page that will be shown if the user enters invalid parameters
     */
    public static void printHelp() {
        String header = "Station that executes sql queries from a transaction manager";
        String footer = "";

        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("dezsys06_station", header, getOptions(), footer, true);
    }

}
