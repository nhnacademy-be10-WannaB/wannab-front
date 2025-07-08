package shop.wannab.frontservice.auth.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaycoUserResponse {
    private Header header;
    private Data data;

    @Getter
    @Setter
    public static class Header {
        private Boolean isSuccessful;
        private int resultCode;
        private String resultMessage;
    }

    @Getter
    @Setter
    public static class Data {
        private Member member;
    }
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Member {
        private String idNo;
        private String mobile;
        private String email;
        private String name;
        private LocalDate birthday;
    }
}