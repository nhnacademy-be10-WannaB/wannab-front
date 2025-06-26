package shop.wannab.frontservice.order.list.deliveryPolicy;

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
import shop.wannab.frontservice.order.list.deliveryPolicy.dto.DeliveryPolicyRequest;
import shop.wannab.frontservice.order.list.deliveryPolicy.dto.DeliveryPolicyResponse;
import shop.wannab.frontservice.order.client.OrderApiClient;

@Controller
@RequestMapping("/admin/delivery-policy")
@RequiredArgsConstructor
public class DeliveryPolicyController {

    private final OrderApiClient orderApiClient;

    //화면
    @GetMapping
    public String deliveryPage(Model model){

        //생성
        if (!model.containsAttribute("request")) {
            model.addAttribute("request", new DeliveryPolicyRequest());
        }
        //정책목록
        List<DeliveryPolicyResponse> list = orderApiClient.deliveryPolicyfindAll();
        model.addAttribute("list", list);

        return "admin/delivery-policy";
    }


    //배송비 정책생성
    @PostMapping
    public String create(@Valid @ModelAttribute("request") DeliveryPolicyRequest request,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "admin/delivery-policy";
        }

        orderApiClient.deliveryPolicyCreate(request);

        //생성시 알림
        redirectAttributes.addFlashAttribute("message", "배송비 정책이 생성되었습니다");
        return "redirect:/admin/delivery-policy";
    }



    //배송비 정책수정
    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("request") DeliveryPolicyRequest request,
                         @RequestParam("id") Long id,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "admin/delivery-policy";
        }
        orderApiClient.deliveryPolicyUpdate(id, request);

        //수정시 알림
        redirectAttributes.addFlashAttribute("message", "배송비 정책이 수정되었습니다");

        return "redirect:/admin/delivery-policy";
    }


    //배송비 정책삭제
    @PostMapping("/delete")
    public String delete(@RequestParam("id") Long id,
                         RedirectAttributes redirectAttributes){
        orderApiClient.deliveryPolicyDelete(id);

        //삭제시 알림
        redirectAttributes.addFlashAttribute("message", "배송비 정책이 삭제되었습니다");
        return "redirect:/admin/delivery-policy";
    }


}
