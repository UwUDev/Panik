package me.uwu.panik;

import com.google.gson.Gson;
import me.uwu.panik.packet.Packet;
import me.uwu.panik.struct.Config;

import java.io.FileReader;
import java.io.IOException;

public class Panik {
    private static long sent = 0;
    public static void stress(Config config) throws IOException {
        System.out.print("\u001B[34mLoading config...");
        Gson gson = new Gson();
        Packet packet = gson.fromJson(new FileReader(config.getPacketPath()), Packet.class);
        packet.setConfig(config);
        System.out.print("\r\u001B[32mConfig loaded ✓       \n");
        System.out.print("\u001B[34mCrafting packets...");
        packet.craftRequest();
        System.out.print("\r\u001B[32mPackets crafted ✓       \n");
        System.out.print("\u001B[35mPress enter to start stressing.\u001B[0m");
        //noinspection ResultOfMethodCallIgnored
        System.in.read();
        if (!config.isDebug())
            new Thread(() -> {
                while (true) {
                    System.out.print("\r\u001B[36m" + sent + " packet sent.");
                    try {
                        //noinspection BusyWait
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();


        // TODO: 18/01/2022 thread pool
        for (int i = 0; i < config.getThreadCount(); i++) {
            new Thread(() -> {
                for (int j = 0; j < config.getIterations(); j++) {
                    try {
                        packet.execute(config.isDebug());
                        sent++;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
