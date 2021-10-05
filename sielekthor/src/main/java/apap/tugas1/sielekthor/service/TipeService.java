package apap.tugas1.sielekthor.service;

import apap.tugas1.sielekthor.model.TipeModel;

import java.math.BigInteger;
import java.util.List;

public interface TipeService {
    void addTipe(TipeModel tipe);
    List<TipeModel> getTipeList();
    TipeModel getTipeByIdTipe(Long idTipe);
}
