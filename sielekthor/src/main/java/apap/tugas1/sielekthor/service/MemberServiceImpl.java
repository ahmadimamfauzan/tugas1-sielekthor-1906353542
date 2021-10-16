package apap.tugas1.sielekthor.service;

import apap.tugas1.sielekthor.model.MemberModel;
import apap.tugas1.sielekthor.repository.MemberDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberDB memberDB;

    @Override
    public void addMember(MemberModel member) {
        memberDB.save(member);
    }

    @Override
    public List<MemberModel> getMemberList() {
        return memberDB.findAll();
    }

    @Override
    public MemberModel getMemberByIdMember(Long idMember) {
        Optional<MemberModel> member = memberDB.findByIdMember(idMember);
        if(member.isPresent()) {
            return member.get();
        }
        return null;
    }

    @Override
    public void ubahMember(MemberModel member) {
        memberDB.save(member);
    }

    @Override
    public void sortMemberByListPembelian(List<MemberModel> listAllMember) {
        QuickSort quickSort = new QuickSort();
        quickSort.sort(listAllMember, 0, listAllMember.size()-1);
    }
}
