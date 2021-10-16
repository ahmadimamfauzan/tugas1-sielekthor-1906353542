package apap.tugas1.sielekthor.repository;

import apap.tugas1.sielekthor.model.PembelianModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PembelianDB extends JpaRepository<PembelianModel, Long> {
    Optional<PembelianModel> findByIdPembelian(Long idPembelian);
}
