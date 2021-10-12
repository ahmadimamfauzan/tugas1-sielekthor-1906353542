package apap.tugas1.sielekthor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Entity
@Table(name = "pembelian_barang")
public class PembelianBarangModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPembelianBarang;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_barang", referencedColumnName = "idBarang", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BarangModel idBarang;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_pembelian", referencedColumnName = "idPembelian", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PembelianModel idPembelian;

    @NotNull
    @Column(nullable = false)
    //@DateTimeFormat(pattern = "DD/MM/YYYY")
    private Date tanggalGaransiPembelianBarang;

    @NotNull
    @Column(nullable = false)
    private Integer quantityPembelianBarang;
}
