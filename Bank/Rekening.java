abstract class Rekening {
    protected double saldo;

    public Rekening(double saldoAwal) {
        this.saldo = saldoAwal;
    }

    public double getSaldo() {
        return saldo;
    }

    public abstract String getTipeRekening();

    public void deposit(double nominal) {
        saldo += nominal;
    }

    public void tarikTunai(double nominal) {
        if (saldo >= nominal) {
            saldo -= nominal;
        } else {
            System.out.println("Saldo tidak mencukupi");
        }
    }
}
