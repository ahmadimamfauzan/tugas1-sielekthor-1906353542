package apap.tugas1.sielekthor.service;

import apap.tugas1.sielekthor.model.PembelianBarangModel;
import apap.tugas1.sielekthor.model.PembelianModel;
import apap.tugas1.sielekthor.repository.PembelianBarangDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class PembelianBarangServiceImpl implements PembelianBarangService {
    @Autowired
    PembelianBarangDB pembelianBarangDB;

    @Override
    public void addPembelianBarang(PembelianBarangModel pembelianBarang) {

        pembelianBarangDB.save(pembelianBarang);
    }

    @Override
    public List<PembelianBarangModel> getPembelianBarangList() {
        return pembelianBarangDB.findAll();
    }

    @Override
    public PembelianBarangModel getPembelianBarangByIdPembelianBarang(Long idPembelianBarang) {
        Optional<PembelianBarangModel> pembelianBarang = pembelianBarangDB.findByIdPembelianBarang(idPembelianBarang);
        if(pembelianBarang.isPresent()) {
            return pembelianBarang.get();
        }
        return null;
    }

    @Override
    public Date hitungTanggalGaransi(PembelianBarangModel pembelianBarangModel) {
        int jumlahGaransiBarang = pembelianBarangModel.getIdBarang().getJumlahGaransiBarang();
        LocalDateTime tanggalPembelian = pembelianBarangModel.getIdPembelian().getTanggalPembelian();
        LocalDateTime tanggalGaransi = tanggalPembelian.plusDays(jumlahGaransiBarang);
        java.sql.Date tanggalGaransiSQL = java.sql.Date.valueOf(tanggalGaransi.toLocalDate());
        return tanggalGaransiSQL;
    }
}
