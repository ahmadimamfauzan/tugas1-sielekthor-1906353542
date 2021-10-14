package apap.tugas1.sielekthor.service;

import apap.tugas1.sielekthor.model.BarangModel;
import apap.tugas1.sielekthor.model.MemberModel;
import apap.tugas1.sielekthor.model.PembelianBarangModel;
import apap.tugas1.sielekthor.model.PembelianModel;
import apap.tugas1.sielekthor.repository.PembelianDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

@Service
@Transactional
public class PembelianServiceImpl implements PembelianService {

    @Autowired
    PembelianDB pembelianDB;

    @Override
    public void addPembelian(PembelianModel pembelian) {
        pembelianDB.save(pembelian);
    }

    @Override
    public List<PembelianModel> getPembelianList() {
        return pembelianDB.findAll();
    }

    @Override
    public PembelianModel getPembelianByIdPembelian(Long idPembelian) {
        Optional<PembelianModel> pembelian = pembelianDB.findByIdPembelian(idPembelian);
        if(pembelian.isPresent()) {
            return pembelian.get();
        }
        return null;
    }

    @Override
    public String createNoInvoicePembelian(PembelianModel pembelian) {
        // Karakter 1
        String namaMember = pembelian.getIdMember().getNamaMember();
        namaMember = namaMember.toLowerCase();
        char inisial = namaMember.charAt(0);
        int karakter1Int = inisial - 'a' + 1;
        karakter1Int %= 10;
        String karakter1 = String.valueOf(karakter1Int);

        // Karakter 2
        String namaAdmin = pembelian.getNamaAdminPembelian();
        char hurufTerakhir = namaAdmin.charAt(namaAdmin.length() - 1);
        String karakter2 = Character.toString(hurufTerakhir).toUpperCase();

        // Karakter 3456 (4 karakter selanjutnya)
        LocalDateTime tanggalPembelian = pembelian.getTanggalPembelian();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String formattedDateTime = tanggalPembelian.format(formatter);
        String tanggal = formattedDateTime.substring(8,10);
        String bulan = formattedDateTime.substring(5,7);
        String karakter3456 = tanggal + bulan;

        // Karakter 78 (2 karakter selanjutnya)
        boolean isCash = pembelian.getIsCashPembelian();
        String karakter78 = (isCash==true ? "02" : "01");

        // Karakter 91011 (3 karakter selanjutnya)
        int operasiTanggalBulan = (Integer.parseInt(tanggal) + Integer.parseInt(bulan)) * 5;
        String karakter91011Sementara = "";
        if(operasiTanggalBulan < 100) {
            karakter91011Sementara = "0" + operasiTanggalBulan;
        } else {
            karakter91011Sementara = String.valueOf(operasiTanggalBulan);
        }
        String karakter91011 =  karakter91011Sementara;

        // Karakter 1213 (2 karakter terakhir)
        String karakter1213Sementara = "";
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random randomNumber = new Random();
        for(int i = 0; i < 2; i++) {
            int randomNo = randomNumber.nextInt(letters.length());
            Character character = letters.charAt(randomNo);
            karakter1213Sementara += Character.toString(character);
        }
        String karakter1213 = karakter1213Sementara;

        // Construct all karakter1-13
        return karakter1 + karakter2 + karakter3456 + karakter78 + karakter91011 + karakter1213;
    }

    @Override
    public int hitungTotalHarga(PembelianModel pembelian) {
        Set<PembelianBarangModel> pembelianBarangSet = pembelian.getSetPembelianBarang();
        int total = 0;
        for(PembelianBarangModel pembelianBarang : pembelianBarangSet) {
            int totalSementara = 0;
            BarangModel barang = pembelianBarang.getIdBarang();
            Integer quantity = pembelianBarang.getQuantityPembelianBarang();
            totalSementara = barang.getHargaBarang() * quantity;
            total += totalSementara;
        }
        return total;
    }

    @Override
    public LocalDateTime createTanggalPembelian(PembelianModel pembelian) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime now = LocalDateTime.now();
        String formattedDateTime = now.format(formatter);
        LocalDateTime tanggalPembelian = LocalDateTime.parse(formattedDateTime);
        return tanggalPembelian;
    }

    @Override
    public int hitungTotalBarang(PembelianModel pembelian) {
        Set<PembelianBarangModel> pembelianBarangModelSet = pembelian.getSetPembelianBarang();
        int count = 0;
        for(PembelianBarangModel pembelianBarang : pembelianBarangModelSet) {
            count += pembelianBarang.getQuantityPembelianBarang();
        }
        return count;
    }
}
