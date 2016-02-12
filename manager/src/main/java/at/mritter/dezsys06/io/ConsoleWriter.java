package at.mritter.dezsys06.io;

public class ConsoleWriter implements Writer {

    @Override
    public void write(String content) {
        System.out.println(content);
    }
}
