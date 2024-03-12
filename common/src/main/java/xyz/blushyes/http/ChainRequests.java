package xyz.blushyes.http;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ChainRequests {
    private String url;
    private final Map<String, String> headers = new HashMap<>();
    private final Map<String, String> payloads = new HashMap<>();
    private final Map<String, String> params = new HashMap<>();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final Requests REQUESTS = new HutoolRequests();

    public ChainRequests url(String url) {
        this.url = url;
        return this;
    }

    public ChainRequests header(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public ChainRequests param(String key, String value) {
        params.put(key, value);
        return this;
    }

    public ChainRequests payload(String key, String value) {
        payloads.put(key, value);
        return this;
    }

    public String get() {
        check();
        return REQUESTS.get(url, params, headers);
    }

    public String post() {
        check();
        try {
            String payload = OBJECT_MAPPER.writeValueAsString(payloads);
            return REQUESTS.post(url, payload, headers);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void check() {
        if (url == null || url.isEmpty()) {
            throw new RuntimeException("url is null");
        }
    }

    public static ChainRequests build() {
        return new ChainRequests();
    }
}
