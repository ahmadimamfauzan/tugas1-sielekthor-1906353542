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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Entity
@Table(name = "member")
public class MemberModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMember;

    @NotNull
    @Column(nullable = false)
    private Integer jenisKelaminMember;

    @NotNull
    @Size(max=255)
    @Column(nullable = false)
    private String namaMember;

    @NotNull
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime tanggalPendaftaranMember;

    @NotNull
    @Column(nullable = false)
    private Date tanggalLahirMember;

    //Relasi dengan PembelianModel
    @OneToMany(mappedBy = "idMember", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PembelianModel> listPembelian;

    // Sort by listPembelian.size()
    public int compareTo(MemberModel otherMemberModel) {
        if(this.getListPembelian().size() != otherMemberModel.getListPembelian().size()) {
            return this.getListPembelian().size() - otherMemberModel.getListPembelian().size();
        } else {
            if(this.getNamaMember().compareTo(otherMemberModel.getNamaMember()) < 1) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}
