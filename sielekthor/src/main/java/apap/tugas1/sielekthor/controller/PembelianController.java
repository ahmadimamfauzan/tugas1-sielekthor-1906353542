package apap.tugas1.sielekthor.controller;

import apap.tugas1.sielekthor.model.*;
import apap.tugas1.sielekthor.service.BarangService;
import apap.tugas1.sielekthor.service.MemberService;
import apap.tugas1.sielekthor.service.PembelianBarangService;
import apap.tugas1.sielekthor.service.PembelianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class PembelianController {
    @Qualifier("pembelianServiceImpl")
    @Autowired
    private PembelianService pembelianService;

    @Qualifier("memberServiceImpl")
    @Autowired
    private MemberService memberService;

    @Qualifier("barangServiceImpl")
    @Autowired
    private BarangService barangService;

    @Qualifier("pembelianBarangServiceImpl")
    @Autowired
    private PembelianBarangService pembelianBarangService;

    @GetMapping("/pembelian")
    public String listPembelian(Model model) {
        List<PembelianModel> listPembelian = pembelianService.getPembelianList();
        model.addAttribute("listPembelian", listPembelian);
        return "view-all-pembelian";
    }

    @GetMapping("/pembelian/tambah")
    public String tambahPembelianForm(Model model) {
        PembelianModel pembelian = new PembelianModel();
        PembelianBarangModel pembelianBarang = new PembelianBarangModel();

        // Barang List yang masih ada stocknya
        List<BarangModel> barangModelList = barangService.getBarangList();
        List<BarangModel> barangModelListStock = new ArrayList<>();
        for(BarangModel barang : barangModelList) {
            if(barang.getStokBarang() != 0) {
                barangModelListStock.add(barang);
            }
        }

        // Wrapper
        List<PembelianBarangModel> wrapperPembelianBarang = new ArrayList<>();
        for(int i = 0; i < barangModelListStock.size(); i++) {
//        for(int i = 0; i < 1; i++) {
            wrapperPembelianBarang.add(new PembelianBarangModel());
        }
        PembelianBarangForm pembelianBarangForm = new PembelianBarangForm();
        pembelianBarangForm.setPembelianBarangList(wrapperPembelianBarang);
        pembelianBarangForm.setListBarang(barangModelListStock);

        model.addAttribute("pembelianBarangForm", pembelianBarangForm);
        model.addAttribute("pembelian", pembelian);
        model.addAttribute("pembelianBarang", pembelianBarang);
        model.addAttribute("listMember", memberService.getMemberList());
        //model.addAttribute("listBarang", barangModelListStock);
        return "form-tambah-pembelian";
    }

    @PostMapping("/pembelian/tambah")
    public String tambahPembelianSubmit(
            @ModelAttribute PembelianModel pembelian,
            @ModelAttribute PembelianBarangModel pembelianBarang,
            @ModelAttribute PembelianBarangForm pembelianBarangForm,
            Model model
    ) {
        // Create pembelian
            // Tanggal Pembelian
            LocalDateTime tanggalPembelian = pembelianService.createTanggalPembelian(pembelian);
            pembelian.setTanggalPembelian(tanggalPembelian);

            // Create invoice
            String noInvoice = pembelianService.createNoInvoicePembelian(pembelian);
            pembelian.setNoInvoicePembelian(noInvoice);

            // Create set pembelian barang
            Set<PembelianBarangModel> pembelianBarangModelSet = new HashSet<>();
            pembelian.setSetPembelianBarang(pembelianBarangModelSet);


        // Create pembelian barang
        for(PembelianBarangModel barangPembelian : pembelianBarangForm.getPembelianBarangList()) {
            if(barangPembelian.getIdBarang() != null) {

                // Assign to pembelian
                barangPembelian.setIdPembelian(pembelian);

                // Masukin ke set pembelian
                pembelian.getSetPembelianBarang().add(barangPembelian);

                // Stok barang berkurang
                BarangModel barangDibeli = barangPembelian.getIdBarang();
                int jumlahBarangDibeli = barangPembelian.getQuantityPembelianBarang();
                barangDibeli.setStokBarang(barangDibeli.getStokBarang() - jumlahBarangDibeli);
                barangService.ubahBarang(barangDibeli);

                // Hitung tanggal garansi
                Date tanggalGaransi = pembelianBarangService.hitungTanggalGaransi(barangPembelian);
                barangPembelian.setTanggalGaransiPembelianBarang(tanggalGaransi);
            }
        }
        // Jumlah Barang
        int jumlahBarangPembelian = pembelianService.hitungTotalBarang(pembelian);
        pembelian.setJumlahBarangPembelian(jumlahBarangPembelian);

        // Total harga pembelian
        int totalHargaPembelian = pembelianService.hitungTotalHarga(pembelian);
        pembelian.setTotalPembelian(totalHargaPembelian);

        // Save pembelian
        pembelianService.addPembelian(pembelian);

        for(PembelianBarangModel barangPembelian : pembelianBarangForm.getPembelianBarangList()) {
            if(barangPembelian.getIdBarang() != null) {
                // Save ke PembelianBarangDB
                pembelianBarangService.addPembelianBarang(barangPembelian);
            }
        }
        model.addAttribute("noInvoicePembelian", pembelian.getNoInvoicePembelian());
        return "tambah-pembelian";
    }

    @GetMapping("pembelian/{idPembelian}")
    public String lihatPembelian(
            @PathVariable Long idPembelian,
            Model model
    ) {
        PembelianModel pembelian = pembelianService.getPembelianByIdPembelian(idPembelian);

        model.addAttribute("pembelian", pembelian);
        model.addAttribute("pembelianBarangSet", pembelian.getSetPembelianBarang());
        model.addAttribute("jumlahBarangPembelian", pembelian.getJumlahBarangPembelian());
        return "view-detail-pembelian";
    }

    @GetMapping("pembelian/hapus/{idPembelian}")
    public String hapusPembelianForm(
        @PathVariable Long idPembelian,
        Model model
    ) {
        PembelianModel pembelian = pembelianService.getPembelianByIdPembelian(idPembelian);
        model.addAttribute("pembelian", pembelian);
        return "form-hapus-pembelian";
    }

    @PostMapping("pembelian/hapus")
    public String hapusPembelianSubmit(
            @ModelAttribute PembelianModel pembelian,
            Model model
    ) {
        Set<PembelianBarangModel> pembelianBarangModelSet = pembelian.getSetPembelianBarang();
        List<BarangModel> barangModelList = barangService.getBarangList();

        // Tambah stok
        List<BarangModel> barangBertambahList = new ArrayList<>();
        for(BarangModel barang : barangModelList) {
            for(PembelianBarangModel pembelianBarang : pembelianBarangModelSet) {
                if(pembelianBarang.getIdBarang().getIdBarang() == barang.getIdBarang()) {
                    int oldStock = barang.getStokBarang();
                    Integer qty = pembelianBarang.getQuantityPembelianBarang();
                    barang.setStokBarang(oldStock + qty);
                    barangBertambahList.add(barang);
                    barangService.ubahBarang(barang);
                }
            }
        }

        model.addAttribute("pembelian", pembelian);
        model.addAttribute("barangBertambahList", barangBertambahList);
        pembelianService.deletePembelian(pembelian);
        return "hapus-pembelian";
    }


    @GetMapping("/filter-pembelian")
    public String cariPembelianForm(Model model) {

        List<PembelianModel> pembelianSearchList = new ArrayList<>();

        model.addAttribute("listMember", memberService.getMemberList());
        model.addAttribute("pembelianSearchList", pembelianSearchList);
        model.addAttribute("sudahCari", false);
        return "cari-pembelian";
    }

    @GetMapping("filter-pembelian/")
    public String cariPembelianSubmit(
            @ModelAttribute MemberModel member,
            @RequestParam(value="memberForm") Long idMember,
            @RequestParam(value="apakahCashForm") Boolean apakahCashForm,
            Model model
    ) {

        List<PembelianModel> pembelianModelList = pembelianService.getPembelianList();
        List<PembelianModel> pembelianSearchList = new ArrayList<>();

        MemberModel memberr = memberService.getMemberByIdMember(idMember);

        for(PembelianModel pembelian : pembelianModelList) {
            if(pembelian.getIdMember().getIdMember() == idMember) {
                if(pembelian.getIsCashPembelian() == apakahCashForm) {
                    pembelianSearchList.add(pembelian);
                }
            }
        }

        model.addAttribute("listMember", memberService.getMemberList());
        model.addAttribute("pembelianSearchList", pembelianSearchList);
        model.addAttribute("memberForm", memberr);
        model.addAttribute("apakahCashForm", apakahCashForm);
        model.addAttribute("sudahCari", true);
        return "cari-pembelian";
    }

}
