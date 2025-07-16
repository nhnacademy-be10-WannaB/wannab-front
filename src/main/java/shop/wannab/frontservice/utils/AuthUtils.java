package shop.wannab.frontservice.utils;

public class AuthUtils {
    public static String addRolePrefix(String role) {
        return "ROLE_"+role;
    }
}
