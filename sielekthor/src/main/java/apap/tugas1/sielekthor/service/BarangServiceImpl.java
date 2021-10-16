package apap.tugas1.sielekthor.service;

import apap.tugas1.sielekthor.model.BarangModel;
import apap.tugas1.sielekthor.repository.BarangDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BarangServiceImpl implements BarangService {

    @Autowired
    BarangDB barangDB;

    @Override
    public void addBarang(BarangModel barang) {
        barangDB.save(barang);
    }

    @Override
    public List<BarangModel> getBarangList() {
        return barangDB.findAll();
    }

    @Override
    public BarangModel getBarangByIdBarang(Long idBarang) {
        Optional<BarangModel> barang = barangDB.findByIdBarang(idBarang);
        if(barang.isPresent()) {
            return barang.get();
        }
        return null;
    }

    @Override
    public void ubahBarang(BarangModel barang) {
        barangDB.save(barang);
    }
}
