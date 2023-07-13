import java.util.ArrayList;

class Bank {
    private static Bank instance; // Menyimpan instance tunggal dari Bank
    private ArrayList<Nasabah> nasabahBank;

    private Bank() {
        nasabahBank = new ArrayList<>();
    }

    public static Bank getInstance() {
        if (instance == null) {
            instance = new Bank();
        }
        return instance;
    }

    public ArrayList<Nasabah> getNasabahBank() {
        return nasabahBank;
    }

    public void tambahNasabah(Nasabah nasabah) {
        nasabahBank.add(nasabah);
    }

    public void hapusNasabah(Nasabah nasabah) {
        nasabahBank.remove(nasabah);
    }

    public Nasabah getNasabah(int index) {
        return nasabahBank.get(index);
    }

    public void transferDana(int rekeningAsal, int rekeningTujuan, double nominal) {
        Nasabah nasabahAsal = nasabahBank.get(rekeningAsal);
        Nasabah nasabahTujuan = nasabahBank.get(rekeningTujuan);
        Rekening rekeningAsalObj = nasabahAsal.getRekening();
        Rekening rekeningTujuanObj = nasabahTujuan.getRekening();

        if (rekeningAsalObj.getSaldo() >= nominal) {
            rekeningAsalObj.tarikTunai(nominal);
            rekeningTujuanObj.deposit(nominal);
        } else {
            System.out.println("Saldo tidak mencukupi untuk transfer dana");
        }
    }
}
