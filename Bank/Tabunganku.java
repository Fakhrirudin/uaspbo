class Tabunganku extends Rekening {
    public Tabunganku(double saldoAwal) {
        super(saldoAwal);
    }

    @Override
    public String getTipeRekening() {
        return "Tabunganku";
    }
}
