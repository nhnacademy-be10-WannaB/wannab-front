package shop.wannab.frontservice.order.list.wrappingPolicy;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.wannab.frontservice.order.client.OrderApiClient;
import shop.wannab.frontservice.order.list.wrappingPolicy.dto.WrappingPaperRequest;
import shop.wannab.frontservice.order.list.wrappingPolicy.dto.WrappingPaperResponse;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/wrapping-papers")
public class WrappingPaperController {

    private final OrderApiClient orderApiClient;


    //화면
    @GetMapping
    public String wrappingPage(Model model){

        //생성
        if (!model.containsAttribute("request")) {
            model.addAttribute("request", new WrappingPaperRequest());
        }

        //포장지 목록
        List<WrappingPaperResponse> list = orderApiClient.wrappingfindAll();
        model.addAttribute("list", list);

        return "admin/wrapping-papers";
    }


    //포장지 생성
    @PostMapping
    public String create(@Valid @ModelAttribute("request") WrappingPaperRequest request,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "admin/wrapping-papers";
        }


        orderApiClient.wrappingPaperCreate(request);

        //생성시 알림
        redirectAttributes.addFlashAttribute("message", "포장지가 생성되었습니다");

        return "redirect:/admin/wrapping-papers";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("request") WrappingPaperRequest request,
                         BindingResult bindingResult,
                         @RequestParam("wpId") Long wpId,
                         Model model,
                         RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            return "admin/wrapping-papers";
        }

        orderApiClient.wrappingPaperUpdate(wpId, request);
        //수정시 알림
        redirectAttributes.addFlashAttribute("message", "포장지가 수정되었습니다");

        return "redirect:/admin/wrapping-papers";
    }

    //포장지 삭제
    @PostMapping("/delete")
    public String delete(@RequestParam("wpId") Long wpId,
                         RedirectAttributes redirectAttributes){
        orderApiClient.wrappingPaperDelete(wpId);

        //삭제시 알림
        redirectAttributes.addFlashAttribute("message", "포장지가 삭제되었습니다");
        return "redirect:/admin/wrapping-papers";
    }



}
