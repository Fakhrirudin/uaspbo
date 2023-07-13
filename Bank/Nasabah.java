class Nasabah {
    private String nama;
    private Rekening rekening;

    public Nasabah(String nama, Rekening rekening) {
        this.nama = nama;
        this.rekening = rekening;
    }

    public String basicInfo() {
        return "Nama Nasabah: " + nama + ", Tipe Rekening: " + rekening.getTipeRekening();
    }

    public Rekening getRekening() {
        return rekening;
    }
}
