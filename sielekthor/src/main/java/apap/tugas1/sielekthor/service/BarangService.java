package apap.tugas1.sielekthor.service;

import apap.tugas1.sielekthor.model.BarangModel;
import apap.tugas1.sielekthor.model.TipeModel;

import java.util.List;

public interface BarangService {
    void addBarang(BarangModel barang);
    List<BarangModel> getBarangList();
    BarangModel getBarangByIdBarang(Long idBarang);
    void ubahBarang(BarangModel barang);
}
