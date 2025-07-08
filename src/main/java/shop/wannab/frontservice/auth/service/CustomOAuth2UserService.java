package shop.wannab.frontservice.auth.service;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import shop.wannab.frontservice.auth.PaycoClient;
import shop.wannab.frontservice.auth.ResponseCode;
import shop.wannab.frontservice.auth.domain.PaycoLoginRequest;
import shop.wannab.frontservice.auth.domain.PaycoLoginResponse;
import shop.wannab.frontservice.auth.domain.PrincipalDetails;
import shop.wannab.frontservice.auth.domain.PaycoUserResponse;
import shop.wannab.frontservice.auth.domain.Response;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final PaycoClient paycoClient;
    private final AuthClient authClient;
    @Value("${spring.security.oauth2.client.registration.payco.client-id}")
    private String clientId;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        PaycoUserResponse response = paycoClient.getPaycoUserInfo(clientId,
                userRequest.getAccessToken().getTokenValue());

        PaycoUserResponse.Member member = response.getData().getMember();

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        Map<String, Object> userinfo = new HashMap<>();
        userinfo.put("idNo", member.getIdNo());
        userinfo.put("email", member.getEmail());
        userinfo.put("phone", member.getMobile());
        userinfo.put("name", member.getName());
        userinfo.put("birthday", member.getBirthday());

        PaycoLoginRequest request = PaycoLoginRequest.builder()
                .name(member.getName())
                .email(member.getEmail())
                .birth(member.getBirthday())
                .phone(member.getMobile())
                .providerId(member.getIdNo())
                .providerName(registrationId)
                .build();

        ResponseEntity<Response<PaycoLoginResponse>> loginResponse = authClient.paycoLogin(request);
        return new PrincipalDetails(
                loginResponse.getBody().getData().id(),
                loginResponse.getBody().getData().role(),
                loginResponse.getBody().getResponseCode().equals(ResponseCode.PAYCO_LOGIN_SUCESS),
                userinfo,
                userNameAttributeName
        );
    }
}
