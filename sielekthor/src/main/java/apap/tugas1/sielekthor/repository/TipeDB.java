package apap.tugas1.sielekthor.repository;

import apap.tugas1.sielekthor.model.TipeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipeDB extends JpaRepository<TipeModel, Long> {
    Optional<TipeModel> findByIdTipe(Long idTipe);
}
