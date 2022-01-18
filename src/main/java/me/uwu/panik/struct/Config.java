package me.uwu.panik.struct;

import lombok.Data;

public @Data class Config {
    // TODO: 18/01/2022 proxy
    private final boolean debug;
    private final int threadCount, iterations;
    private final String packetPath;
}
