package ru.prox.service;

import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
public class GoogleService {
    public static final String PROTECTED_RESOURCE_URL = "https://www.googleapis.com/oauth2/v3/userinfo";
    final static String clientId = "766389803210-7br1su61j5cdglrqhqv5h0qcbmf35u1b.apps.googleusercontent.com";
    final static String clientSecret = "F29yda9O1cZ60GD_g3_mDOdC";
    final static String secretState = "secret" + new Random().nextInt(999_999);
    static OAuth20Service service;

    @GetMapping("/google/auth")
    public String googleLogIn() {
        final Map<String, String> additionalParams = new HashMap<>();
        additionalParams.put("access_type", "offline");
        additionalParams.put("prompt", "consent");
        final String authorizationUrl = getOAuth20GoogleService().createAuthorizationUrlBuilder()
                .state(secretState)
                .additionalParams(additionalParams)
                .build();
        return "redirect:" + authorizationUrl;
    }

    public static OAuth20Service getOAuth20GoogleService() {
        if (service == null) {
            service = new ServiceBuilder(clientId)
                    .apiSecret(clientSecret)
                    .defaultScope("email profile openid")
                    .callback("http://localhost:8080/callback")
                    .build(GoogleApi20.instance());
        }
        return service;
    }
}
