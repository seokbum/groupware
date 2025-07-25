package ldb.groupware.controller.admin;

import jakarta.validation.Valid;
import ldb.groupware.dto.member.MemberFormDto;
import ldb.groupware.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {
    private final MemberService memberService;

    public AdminController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("dashBoard")
    public String dashBoard() {
        return "admin/dashBoard";
    }

    @GetMapping("itemListManage")
    public String itemListManage() {
        return "admin/itemListManage";
    }

    @GetMapping("itemRegistForm")
    public String itemRegistForm() {
        return "admin/itemRegistForm";
    }

    @GetMapping("meetingRoomManage")
    public String meetingRoomManage() {
        return "admin/meetingRoomManage";
    }

    @GetMapping("meetingRoomRegisterForm")
    public String meetingRoomRegisterForm() {
        return "admin/meetingRoomRegisterForm";
    }

    @GetMapping("vehicleManage")
    public String vehicleManage() {
        return "admin/vehicleManage";
    }

    @GetMapping("vehicleRegisterForm")
    public String vehicleRegisterForm() {
        return "admin/vehicleRegisterForm";
    }

    @GetMapping("calendarWrite")
    public String calendarWrite() {
        return "admin/calendarWrite";
    }

    @GetMapping("deptAuth")
    public String deptAuth() {
        return "admin/deptAuth";
    }

    @GetMapping("commTypeManage")
    public String CommTypeManage() {
        return "admin/commTypeManage";
    }

    // 사원 관리 페이지 
    @GetMapping("memberList")
    public String getMemberList(Model model) {
        model.addAttribute("deptList", memberService.getDeptList());
        model.addAttribute("rankList", memberService.getRankList());
        return "admin/memberList";
    }

    // 사원 등록 페이지
    @GetMapping("memberForm")
    public String getMemberForm(Model model) {
        model.addAttribute("deptList", memberService.getDeptList());
        model.addAttribute("rankList", memberService.getRankList());
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "admin/memberForm";
    }
    
    // 사원 등록
    @PostMapping("insertMemberByMng")
    public String insertMemberByMng(@Valid @ModelAttribute("memberFormDto") MemberFormDto dto,
                                    BindingResult bindingResult,
                                    Model model,
                                    @RequestParam(value = "photo", required = false) MultipartFile file) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("deptList", memberService.getDeptList());
            model.addAttribute("rankList", memberService.getRankList());
            return "admin/memberForm";
        }

        boolean success = memberService.insertMember(dto, file);
        if (success) {
            model.addAttribute("url", "/admin/memberList");
            return "alert";
        } else {
            model.addAttribute("msg", "사원 등록 실패");
            model.addAttribute("url", "/admin/memberForm");
            return "alert";
        }
    }
}