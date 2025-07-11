package shop.wannab.frontservice.order.list.paving;

import jakarta.servlet.http.HttpServletRequest;
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
import shop.wannab.frontservice.order.list.paving.dto.PavingRequest;
import shop.wannab.frontservice.order.list.paving.dto.PavingResponse;


@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/paving")
public class PavingController {

    private final OrderApiClient orderApiClient;

    @GetMapping
    public String pavingPage(Model model,
                             HttpServletRequest httpServletRequest){

        model.addAttribute("currentUri", httpServletRequest.getRequestURI());



        if (!model.containsAttribute("request")) {
            model.addAttribute("request", new PavingRequest());
        }
        // 포장지목록
        List<PavingResponse> list = orderApiClient.getPavingList();
        model.addAttribute("list", list);

        return "admin/paving";

    }

    @PostMapping
    public String create(@Valid @ModelAttribute("request") PavingRequest request,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "admin/paving";
        }
        orderApiClient.createPaving(request);

        // 생성시 알림
        redirectAttributes.addFlashAttribute("message", "포장지가 생성되었습니다.");
        return "redirect:/admin/paving";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("request") PavingRequest request,
                         @RequestParam("id") Long id,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "admin/paving";
        }
        orderApiClient.updatePaving(request, id);

        // 수정시 알림
        redirectAttributes.addFlashAttribute("message", " 수정되었습니다");

        return "redirect:/admin/paving";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") Long id,
                         RedirectAttributes redirectAttributes){
        orderApiClient.deletePaving(id);

        // 삭제시 알림
        redirectAttributes.addFlashAttribute("message", "포장지가 삭제되었습니다");
        return "redirect:/admin/paving";
    }

}


/*



@PostMapping("/delete")
public String delete(@RequestParam("id") Long id,
                     RedirectAttributes redirectAttributes){
    orderApiClient.deliveryPolicyDelete(id);

    // 삭제시 알림
    redirectAttributes.addFlashAttribute("message", "배송비 정책이 삭제되었습니다");
    return "redirect:/admin/delivery-policy";
}

 */