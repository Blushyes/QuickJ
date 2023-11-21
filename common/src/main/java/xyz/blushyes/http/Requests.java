package xyz.blushyes.http;

import java.util.Map;

public interface Requests {
    String get(String url, Map<String, String> params);

    String get(String url, Map<String, String> params, Map<String, String> headers);

    String post(String url, String body);

    String post(String url, String body, Map<String, String> headers);

    void setBaseUrl(String baseUrl);
}
