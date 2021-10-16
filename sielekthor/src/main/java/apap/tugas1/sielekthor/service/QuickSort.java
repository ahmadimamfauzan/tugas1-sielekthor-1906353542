package apap.tugas1.sielekthor.service;

import apap.tugas1.sielekthor.model.MemberModel;

import java.util.List;

public class QuickSort {

    public int partition(List<MemberModel> listMemberModel, int low, int high) {
        MemberModel memberPivot = listMemberModel.get(high);
        int i = (low-1);
        for(int j=low; j<high; j++) {
            if(listMemberModel.get(j).compareTo(memberPivot) > 0) {
                i++;

                //swap
                MemberModel iMember = listMemberModel.get(i);
                MemberModel jMember = listMemberModel.get(j);
                listMemberModel.set(i, jMember);
                listMemberModel.set(j, iMember);
            }
        }
        // menukar member pivot ke i+1 (posisi sebenernya)
        MemberModel iplus1Member = listMemberModel.get(i+1);
        MemberModel highMember = listMemberModel.get(high);
        listMemberModel.set(i+1, highMember);
        listMemberModel.set(high, iplus1Member);

        return i+1;
    }

    public void sort(List<MemberModel> listMemberModel, int low, int high) {
        if(low < high) {
            int pi = partition(listMemberModel, low, high);

            // mensorting
            sort(listMemberModel, low, pi-1);
            sort(listMemberModel, pi+1, high);
        }
    }
}
