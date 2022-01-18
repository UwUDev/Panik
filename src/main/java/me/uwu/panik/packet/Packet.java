package me.uwu.panik.packet;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import me.uwu.panik.struct.Config;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;

public @Data class Packet {
    private Config config;
    private final String url;
    private final RequestType requestType;
    private final Map<String, String> headers;
    private final String mediaType, body;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Call packet = null;

    public void craftRequest() {
        // TODO: 18/01/2022 proxy
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder().url(url);
        
        if (requestType == RequestType.GET)
            builder.get();
        else {
            MediaType mediaType = MediaType.parse(this.mediaType);
            RequestBody reqBody = RequestBody.create(mediaType, body);
            if (requestType == RequestType.POST)
                builder.post(reqBody);
            else if (requestType == RequestType.PATCH)
                builder.post(reqBody);
            else if (requestType == RequestType.DELETE)
                builder.delete(reqBody);
            else if (requestType == RequestType.PUT)
                builder.put(reqBody);
            else if (requestType == RequestType.OPTION)
                builder.method("OPTIONS", reqBody);
        }

        headers.forEach(builder::addHeader);
        packet = client.newCall(builder.build());
    }

    public void execute(boolean print) throws IOException {
        if (packet == null)
            throw new PacketExecutionException("Packet not crafted", new Exception());
        if (print)
            System.out.println(packet.clone().execute().body().string());
        else packet.clone().execute();
    }
}
