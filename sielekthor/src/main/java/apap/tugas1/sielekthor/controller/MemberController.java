package apap.tugas1.sielekthor.controller;

import apap.tugas1.sielekthor.model.*;
import apap.tugas1.sielekthor.service.MemberService;
import apap.tugas1.sielekthor.service.PembelianService;
import apap.tugas1.sielekthor.service.TipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MemberController {
    @Qualifier("memberServiceImpl")
    @Autowired
    private MemberService memberService;

    @Qualifier("pembelianServiceImpl")
    @Autowired
    private PembelianService pembelianService;

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

    @GetMapping("member/ubah/{idMember}")
    public String ubahMemberForm(
            @PathVariable Long idMember,
            Model model
    ) {
        MemberModel member = memberService.getMemberByIdMember(idMember);
        model.addAttribute("member", member);
        model.addAttribute("oldName", member.getNamaMember());

        return "form-ubah-member";
    }

    @PostMapping("member/ubah")
    public String ubahMemberSubmit(
            @ModelAttribute MemberModel member,
            @ModelAttribute String oldName,
            Model model
    ) {
        // Get list pembelian by member
        List<PembelianModel> pembelianByMemberList = new ArrayList<>();
        List<PembelianModel> pembelianModelList = pembelianService.getPembelianList();
        for(PembelianModel pembelian : pembelianModelList) {
            if(pembelian.getIdMember().getIdMember() == member.getIdMember()) {
                pembelianByMemberList.add(pembelian);
            }
        }
        List<PembelianModel> copyPembelianByMemberList = new ArrayList<>(pembelianByMemberList);

        if(member.getNamaMember() != oldName) {
            for(PembelianModel pembelian : pembelianByMemberList) {
                String newName = member.getNamaMember();
                String noInvoiceBaru = pembelianService.updateNoInvoicePembelian(newName, pembelian);
                pembelian.setNoInvoicePembelian(noInvoiceBaru);
                pembelianService.ubahPembelian(pembelian);
            }
        }
        memberService.ubahMember(member);
        model.addAttribute("namaMember", member.getNamaMember());
        model.addAttribute("copyPembelianByMemberList", copyPembelianByMemberList);
        model.addAttribute("pembelianByMemberList", pembelianByMemberList);
        return "ubah-member";
    }

    @GetMapping("/bonus/cari/member/paling-banyak")
    public String bonus(Model model) {
        List<MemberModel> memberModelList = memberService.getMemberList();
        memberService.sortMemberByListPembelian(memberModelList);
        model.addAttribute("listMemberSorted", memberModelList);
        return "top-member";
    }
}
