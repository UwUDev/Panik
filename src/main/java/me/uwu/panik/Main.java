package me.uwu.panik;

import com.google.gson.Gson;
import me.uwu.panik.struct.Config;

import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.err.println("No argument given..");
            System.exit(0);
        }
        String configPath = null;
        String packetPath = null;
        int threadCount = -1;
        int iterations = -1;
        boolean debug = false;
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];

            if (arg.equalsIgnoreCase("-c")) {
                try {
                    configPath = args[i+1];
                } catch (ArrayIndexOutOfBoundsException ignored) {
                    System.err.println("-c argument is empty");
                }
            }

            if (arg.equalsIgnoreCase("-p")) {
                try {
                    packetPath = args[i+1];
                } catch (ArrayIndexOutOfBoundsException ignored) {
                    System.err.println("-p argument is empty");
                }
            }

            if (arg.equalsIgnoreCase("-i")) {
                try {
                    iterations = Integer.parseInt(args[i+1]);
                } catch (ArrayIndexOutOfBoundsException ignored) {
                    System.err.println("-i argument is empty or invalid");
                }
            }

            if (arg.equalsIgnoreCase("-t")) {
                try {
                    threadCount = Integer.parseInt(args[i+1]);
                } catch (ArrayIndexOutOfBoundsException ignored) {
                    System.err.println("-t argument is empty or invalid");
                }
            }

            if (arg.equalsIgnoreCase("-d"))
                debug = true;

            if (arg.equalsIgnoreCase("-cli")) {
                System.err.println("Cli menu not implemented.");
                System.exit(2);
            }
        }
        Config config = null;
        if (configPath != null)
             config = new Gson().fromJson(new FileReader("config.json"), Config.class);
        else if (packetPath != null && iterations >=0 && threadCount >=0) {
            config = new Config(debug, threadCount, iterations, packetPath);
        } else {
            System.err.println("Missing arguments...\n\n");
            System.out.println(
                    "Using config file:\n" +
                            "  -c [config path]\n" +
                            "    Ex: java -jar Panik.jar -c config.json\n" +
                            "\n" +
                            "Without config file:\n" +
                            "  -p [packet file path]                                     <REQUIRED>\n" +
                            "  -t [numer of thread]                  (max 2147483646)    <REQUIRED>\n" +
                            "  -i [number of iteration per thread]   (max 2147483646)    <REQUIRED>\n" +
                            "  -d                                    (debug)             <OPTIONAL>\n" +
                            "    Ex: java -jar Panik.jar -p packet.json -t 50 -i 10\n" +
                            "    Ex: java -jar Panik.jar -p packet.json -t 50 -i 10 -d"
            );
            System.exit(3);
        }
        Panik.stress(config);
    }
}
