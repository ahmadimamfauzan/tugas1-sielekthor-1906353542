package apap.tugas1.sielekthor.controller;

import apap.tugas1.sielekthor.model.BarangModel;
import apap.tugas1.sielekthor.model.TipeModel;
import apap.tugas1.sielekthor.service.BarangService;
import apap.tugas1.sielekthor.service.TipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BarangController {

    @Qualifier("barangServiceImpl")
    @Autowired
    private BarangService barangService;

    @Qualifier("tipeServiceImpl")
    @Autowired
    private TipeService tipeService;

    @GetMapping("/barang")
    public String listBarang(Model model) {
        List<BarangModel> listBarang = barangService.getBarangList();
        model.addAttribute("listBarang", listBarang);
        return "view-all-barang";
    }

    @GetMapping("/barang/tambah")
    public String tambahBarangForm(Model model) {
        model.addAttribute("barang", new BarangModel());
        model.addAttribute("listTipe", tipeService.getTipeList());
        return "form-tambah-barang";
    }

    @PostMapping("/barang/tambah")
    public String tambahBarangSubmit(
            @ModelAttribute BarangModel barang,
            Model model
    ) {
        barangService.addBarang(barang);
        model.addAttribute("kodeBarang", barang.getKodeBarang());
        return "tambah-barang";
    }

    @GetMapping("barang/{idBarang}")
    public String lihatBarang(
        @PathVariable Long idBarang,
        Model model
    ) {
        BarangModel barang = barangService.getBarangByIdBarang(idBarang);
        model.addAttribute("barang", barang);
        return "view-detail-barang";
    }

    @GetMapping("barang/ubah/{idBarang}")
    public String ubahBarangForm(
            @PathVariable Long idBarang,
            Model model
    ) {
        BarangModel barang = barangService.getBarangByIdBarang(idBarang);
        model.addAttribute("barang", barang);
        model.addAttribute("listTipe", tipeService.getTipeList());
        return "form-ubah-barang";
    }

    @PostMapping("barang/ubah")
    public String ubahBarangSubmit(
        @ModelAttribute BarangModel barang,
        Model model
    ) {
        barangService.ubahBarang(barang);
        model.addAttribute("kodeBarang", barang.getKodeBarang());
        return "ubah-barang";
    }

    @GetMapping("barang/cari")
    public String cariBarangForm(Model model) {
        model.addAttribute("listTipe", tipeService.getTipeList());
        model.addAttribute("barangSearchList", new ArrayList<BarangModel>());
        model.addAttribute("sudahCari", false);
        return "cari-barang";
    }

    @GetMapping("barang/cari/")
    public String cariBarangSubmit(
            @ModelAttribute TipeModel tipe,
            @RequestParam(value="idTipe") Long idTipe,
            @RequestParam(value="stokAda") Boolean stokAda,
            Model model
    ) {
        List<BarangModel> barangModelList = barangService.getBarangList();

        // Filter sesuai tipe
        List<BarangModel> barangModelTipe = new ArrayList<>();
        TipeModel tipeDicari = tipeService.getTipeByIdTipe(idTipe);
        for(BarangModel barang : barangModelList) {
            if(barang.getTipe().getNamaTipe().equals(tipeDicari.getNamaTipe())) {
                barangModelTipe.add(barang);
            }
        }

        // Filter sesuai stok
        List<BarangModel> barangSesuaiStok = new ArrayList<>();
        for(BarangModel barang : barangModelTipe) {
            if(stokAda == true) {
                if(barang.getStokBarang() > 0) {
                    barangSesuaiStok.add(barang);
                }
            } else {
                if(barang.getStokBarang() == 0) {
                    barangSesuaiStok.add(barang);
                }
            }
        }

        model.addAttribute("listTipe", tipeService.getTipeList());
        model.addAttribute("barangSearchList", barangSesuaiStok);
        model.addAttribute("sudahCari", true);
        model.addAttribute("tipeDicari", tipeDicari);
        model.addAttribute("stokAda", stokAda);
        return "cari-barang";
    }
}
