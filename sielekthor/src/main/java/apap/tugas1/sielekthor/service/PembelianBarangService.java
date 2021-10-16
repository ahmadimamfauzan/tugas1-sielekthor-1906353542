package apap.tugas1.sielekthor.service;

import apap.tugas1.sielekthor.model.PembelianBarangModel;

import java.sql.Date;
import java.util.List;

public interface PembelianBarangService {
    void addPembelianBarang(PembelianBarangModel pembelian);
    List<PembelianBarangModel> getPembelianBarangList();
    PembelianBarangModel getPembelianBarangByIdPembelianBarang(Long idPembelianBarang);
    Date hitungTanggalGaransi(PembelianBarangModel pembelianBarangModel);
}
