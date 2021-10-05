package apap.tugas1.sielekthor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Entity
@Table(name = "barang")
public class BarangModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBarang;

    @NotNull
    @Size(max=255)
    @Column(nullable = false)
    private String namaBarang;

    @NotNull
    @Column(nullable = false)
    private int stokBarang;

    @NotNull
    @Column(nullable = false)
    private int jumlahGaransiBarang;

    @NotNull
    @Size(max=255)
    @Column(nullable = false)
    private String deskripsiBarang;

    @NotNull
    @Size(max=255)
    @Column(nullable = false)
    private String kodeBarang;

    @NotNull
    @Size(max=255)
    @Column(nullable = false)
    private String merkBarang;

    @NotNull
    @Column(nullable = false)
    private int hargaBarang;

    //Relasi dengan TipeModel
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_tipe", referencedColumnName = "idTipe", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TipeModel tipe;
}
