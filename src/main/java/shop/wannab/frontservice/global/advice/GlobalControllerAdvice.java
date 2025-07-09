//package shop.wannab.frontservice.global.advice;
//
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import shop.wannab.frontservice.auth.exception.InactiveUserException;
//
//@ControllerAdvice
//public class GlobalControllerAdvice {
//    @ExceptionHandler({InactiveUserException.class})
//    public String handleInactiveUserException(InactiveUserException e, Model model) {
//        model.addAttribute("userId", e.getMessage());
//        return "/auth/unlock";
//    }
//}
