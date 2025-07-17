package shop.wannab.frontservice.auth.domain;

import io.jsonwebtoken.Claims;
import java.util.Map;

public record TokenPayloadResponse(Map<String, Object> claims) {
}
