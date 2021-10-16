package apap.tugas1.sielekthor.model;

import java.util.List;

public class PembelianBarangForm {
    private List<PembelianBarangModel> pembelianBarangList;
    private List<BarangModel> listBarang;

    public List<PembelianBarangModel> getPembelianBarangList() {
        return pembelianBarangList;
    }

    public void setPembelianBarangList(List<PembelianBarangModel> pembelianBarangList) {
        this.pembelianBarangList = pembelianBarangList;
    }

    public List<BarangModel> getListBarang() {
        return listBarang;
    }

    public void setListBarang(List<BarangModel> listBarang) {
        this.listBarang = listBarang;
    }
}
