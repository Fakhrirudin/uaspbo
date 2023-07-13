class Xpresi extends Rekening {
    public Xpresi(double saldoAwal) {
        super(saldoAwal);
    }

    @Override
    public String getTipeRekening() {
        return "Xpresi";
    }
}
