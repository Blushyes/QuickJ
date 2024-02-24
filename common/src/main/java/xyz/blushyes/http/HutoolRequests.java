package xyz.blushyes.http;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import xyz.blushyes.exception.BaseException;

@Component
@Slf4j
public class HutoolRequests implements Requests {
    private String BASE_URL = "";

    private HttpRequest createPost(String url, String body, Map<String, String> headers) {
        HttpRequest request = HttpUtil.createPost(BASE_URL + url);
        request.body(body);
        Optional.ofNullable(headers).orElse(Collections.emptyMap()).forEach(request::header);
        return request;
    }

    private HttpRequest createPost(String url, String body) {
        return createPost(url, body, null);
    }

    private HttpRequest createGet(String url, Map<String, String> params, Map<String, String> headers) {
        HttpRequest request = HttpUtil.createGet(BASE_URL + url);
        Optional.ofNullable(params).orElse(Collections.emptyMap()).forEach(request::form);
        Optional.ofNullable(headers).orElse(Collections.emptyMap()).forEach(request::header);
        return request;
    }

    private HttpRequest createGet(String url, Map<String, String> params) {
        return createGet(url, params, null);
    }

    @Override
    public String get(String url, Map<String, String> params) {
        return get(url, params, null);
    }

    @Override
    public String get(String url, Map<String, String> params, Map<String, String> headers) {
        HttpRequest request = createGet(url, params, headers);
        HttpResponse response = request.execute();
        if (response.getStatus() != 200) {
            log.error(response.body());
            throw new BaseException(response.body(), response.getStatus());
        }
        return response.body();
    }

    @Override
    public String post(String url, String body) {
        return post(url, body, null);
    }

    @Override
    public String post(String url, String body, Map<String, String> headers) {
        HttpRequest request = createPost(url, body, headers);
        HttpResponse response = request.execute();
        if (response.getStatus() != 200) {
            log.error(response.body());
            throw new BaseException(response.body(), response.getStatus());
        }
        return response.body();
    }

    @Override
    public void setBaseUrl(String baseUrl) {
        this.BASE_URL = baseUrl;
    }
}
