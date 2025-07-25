package ldb.groupware.controller.board;

import jakarta.validation.Valid;
import ldb.groupware.dto.board.FaqFormDto;
import ldb.groupware.dto.member.DeptDto;
import ldb.groupware.dto.board.PaginationDto;
import ldb.groupware.service.board.FaqService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")
public class FaqController {

    private  final FaqService faqService;

    public FaqController(FaqService faqService) {
        this.faqService = faqService;
    }

    @GetMapping("getFaqList")
    public String faqList(Model model,   @RequestParam(value = "page", defaultValue = "1") int currentPage) {
        PaginationDto pageDto = new PaginationDto();
        pageDto.setPage(currentPage);

        Map<String, Object> map = faqService.findFaqList(pageDto);
        pageDto = (PaginationDto)map.get("pageDto");
        System.out.println("faqListPageDto = " + pageDto);
        model.addAttribute("faq", map.get("list"));
        model.addAttribute("pageDto", pageDto);
        return "board/faqList";
    }

    //관리자의 자주묻는질문 관리페이지(권한 및 세션검증필요)
    @GetMapping("getFaqListManage")
    public String faqManage(Model model , @RequestParam(value = "page", defaultValue = "1") int currentPage){
        PaginationDto pageDto = new PaginationDto();
        pageDto.setPage(currentPage);

        Map<String, Object> map = faqService.findFaqList(pageDto);
        pageDto = (PaginationDto)map.get("pageDto");
        System.out.println("pageDto = " + pageDto);
        model.addAttribute("faq", map.get("list"));
        model.addAttribute("pageDto", pageDto);
        return "board/faqListManage";
    }

    //(권한 및 세션검증필요)
    @GetMapping("getFaqForm")
    public String getFaqForm(Model model){
        List<DeptDto> dept = faqService.findDept();
        model.addAttribute("dept", dept);
        return"board/faqForm";
    }

    //(권한 및 세션검증필요)
    @PostMapping("insertFaqByMng")
    public String insertFaqByMng(@Valid @ModelAttribute("faqFormDto")FaqFormDto dto , BindingResult bresult,Model model){
        if(bresult.hasErrors()){
            List<DeptDto> dept = faqService.findDept();
            model.addAttribute("dept", dept);
            return  "board/faqForm";
        }
        int count = faqService.insertFaq(dto);
        System.out.println("count : " + count);
        if(count>0){
           model.addAttribute("msg","등록성공");
           model.addAttribute("url","getFaqListManage?page="+count);
       }
       else{
           model.addAttribute("msg","등록실패");
       }
        return "alert";
    }

    //페이지접근 전 권한체크 추가
    @GetMapping("getFaqEditForm")
    public String getFaqEditForm(@RequestParam("id") String faqId,
                                      @RequestParam(value = "page", defaultValue = "1") int currentPage,
                                      Model model){
        FaqFormDto dto = faqService.findById(faqId);
        List<DeptDto> deptDtos = faqService.deptAll();
        model.addAttribute("faq", dto);
        model.addAttribute("dept", deptDtos);
        return "board/faqEditForm";
    }

    //권한설정필요
    @PostMapping("updateFaqByMng")
    public String updateFaqByMng(@Valid FaqFormDto dto, BindingResult bresult,Model model){
        if(bresult.hasErrors()){
            List<DeptDto> deptDtos = faqService.deptAll();
            model.addAttribute("faq", dto);
            model.addAttribute("dept", deptDtos);
            return "board/faqEditForm";
        }
        if(faqService.updateFaq(dto)){
            model.addAttribute("msg","업데이트성공");
        }
        else{
            model.addAttribute("msg","업데이트실패");
        }
        return "alert";
    }

    @GetMapping("deleteFaqByMng")
    public String deleteFaqByMng(@RequestParam("id") String faqId,
                                 @RequestParam(value = "page", defaultValue = "1") int currentPage,
                                 Model model){
        if(faqService.deleteFaq(faqId)){
            model.addAttribute("msg","삭제성공");
        }
        else{
            model.addAttribute("msg","삭제실패");
        }
        model.addAttribute("url","getFaqListManage?page="+currentPage);
        return "alert";
    }





}
