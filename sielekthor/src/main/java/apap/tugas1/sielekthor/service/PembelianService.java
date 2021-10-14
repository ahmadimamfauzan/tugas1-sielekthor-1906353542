package apap.tugas1.sielekthor.service;

import apap.tugas1.sielekthor.model.PembelianModel;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

public interface PembelianService {
    void addPembelian(PembelianModel pembelian);
    List<PembelianModel> getPembelianList();
    PembelianModel getPembelianByIdPembelian(Long idPembelian);
    //void ubahMember(MemberModel barang);
    String createNoInvoicePembelian(PembelianModel pembelian);
    int hitungTotalHarga(PembelianModel pembelian);
    LocalDateTime createTanggalPembelian(PembelianModel pembelian);
    int hitungTotalBarang(PembelianModel pembelian);
}
