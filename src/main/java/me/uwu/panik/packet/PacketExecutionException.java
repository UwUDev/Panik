package me.uwu.panik.packet;

public class PacketExecutionException extends RuntimeException{
    public PacketExecutionException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
