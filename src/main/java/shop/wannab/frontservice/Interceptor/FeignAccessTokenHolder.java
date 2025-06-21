package shop.wannab.frontservice.Interceptor;

public class FeignAccessTokenHolder {
    private static final ThreadLocal<String> accessTokenHolder = new ThreadLocal<>();

    public static void set(String token) {
        accessTokenHolder.set(token);
    }

    public static String get() {
        return accessTokenHolder.get();
    }

    public static void clear() {
        accessTokenHolder.remove();
    }
}
