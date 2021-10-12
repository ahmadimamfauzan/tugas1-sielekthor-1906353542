package apap.tugas1.sielekthor.service;

import apap.tugas1.sielekthor.model.MemberModel;

import java.util.List;

public interface MemberService {
    void addMember(MemberModel member);
    List<MemberModel> getMemberList();
    MemberModel getMemberByIdMember(Long idMember);
    void ubahMember(MemberModel barang);
}
