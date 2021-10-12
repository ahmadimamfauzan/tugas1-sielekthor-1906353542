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
@Table(name = "pembelian")
public class PembelianModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPembelian;

    @NotNull
    @Column(nullable = false)
    private int totalPembelian;

    @NotNull
    @Column(nullable = false)
    //@DateTimeFormat(pattern = "YYYY-MM-DD")
    private LocalDateTime tanggalPembelian;

    @NotNull
    @Size(max=255)
    @Column(nullable = false)
    private String namaAdminPembelian;

    @NotNull
    @Size(max=255)
    @Column(nullable = false, unique = true)
    private String noInvoicePembelian;

    @NotNull
    @Column(nullable = false)
    private boolean isCashPembelian;

    //Relasi dengan Member
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_member", referencedColumnName = "idMember", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MemberModel idMember;

    //Relasi dengan PembelianBarangModel
    @OneToMany(mappedBy = "idPembelian", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PembelianBarangModel> listPembelianBarang;
}
