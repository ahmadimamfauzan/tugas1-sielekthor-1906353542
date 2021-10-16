package apap.tugas1.sielekthor.service;

import apap.tugas1.sielekthor.model.TipeModel;
import apap.tugas1.sielekthor.repository.TipeDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TipeServiceImpl implements TipeService {

    @Autowired
    TipeDB tipeDB;

    @Override
    public void addTipe(TipeModel tipe) {
        tipeDB.save(tipe);
    }

    @Override
    public List<TipeModel> getTipeList() {
        return tipeDB.findAll();
    }

    @Override
    public TipeModel getTipeByIdTipe(Long idTipe) {
        Optional<TipeModel> tipe = tipeDB.findByIdTipe(idTipe);
        if(tipe.isPresent()) {
            return tipe.get();
        }
        return null;
    }
}
