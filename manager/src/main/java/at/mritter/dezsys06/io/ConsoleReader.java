package at.mritter.dezsys06.io;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleReader implements Reader {

    public static final Logger LOG = LogManager.getLogger(ConsoleReader.class);
    private InputProcessor processor;

    public ConsoleReader(InputProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void read() {
        new Thread(() -> {

            LOG.info("Please enter a message or \"exit\" to exit");

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            // read from the console till the user enters "exit"
            while (true) {
                String line = null;
                try {
                    line = br.readLine();
                    if (line.equals("exit")) {
                        processor.exitApplication();
                        break;
                    }
                    processor.process(line);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }
}
