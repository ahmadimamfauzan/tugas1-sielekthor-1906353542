package apap.tugas1.sielekthor.controller;

import apap.tugas1.sielekthor.model.BarangModel;
import apap.tugas1.sielekthor.model.MemberModel;
import apap.tugas1.sielekthor.model.TipeModel;
import apap.tugas1.sielekthor.service.MemberService;
import apap.tugas1.sielekthor.service.TipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {
    @Qualifier("memberServiceImpl")
    @Autowired
    private MemberService memberService;

    @GetMapping("/member")
    public String listMember(Model model) {
        List<MemberModel> listMember = memberService.getMemberList();
        model.addAttribute("listMember", listMember);
        return "view-all-member";
    }

    @GetMapping("/member/tambah")
    public String tambahMemberForm(Model model) {
        model.addAttribute("member", new MemberModel());
        return "form-tambah-member";
    }

    @PostMapping("/member/tambah")
    public String tambahMemberSubmit(
            @ModelAttribute MemberModel member,
            Model model
    ) {
        memberService.addMember(member);
        model.addAttribute("namaMember", member.getNamaMember());
        return "tambah-member";
    }
}
