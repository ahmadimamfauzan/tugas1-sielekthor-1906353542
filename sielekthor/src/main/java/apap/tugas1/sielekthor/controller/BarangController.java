package apap.tugas1.sielekthor.controller;

import apap.tugas1.sielekthor.model.BarangModel;
import apap.tugas1.sielekthor.model.TipeModel;
import apap.tugas1.sielekthor.service.BarangService;
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
}
