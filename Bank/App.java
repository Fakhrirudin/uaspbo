import java.util.ArrayList;
import java.util.Scanner;

public class App {
    Scanner keyboard = new Scanner(System.in);
    Bank bank = Bank.getInstance();
    Nasabah nasabah;
    String tipeRekening;
    String namaNasabah;
    double jumlahDeposit;

    public static void main(String[] args) {
        App app = new App();
        app.jalankanMenu();
    }

    public void jalankanMenu() {
        int pilihan;
        do {
            System.out.println("+--------------------------------+");
            System.out.println("|        Selamat Datang di       |");
            System.out.println("|            Bank ABC            |");
            System.out.println("+--------------------------------+");
            System.out.println("\n#### Pilih menu dibawah ini! ####");
            System.out.println("1) Buat rekening baru");
            System.out.println("2) Deposit");
            System.out.println("3) Tarik Tunai");
            System.out.println("4) Tampilkan daftar rekening");
            System.out.println("5) Transfer Dana");
            System.out.println("6) Info Saldo");
            System.out.println("7) Penutupan Rekening");
            System.out.println("0) Exit");
            pilihan = getInput();
            proses(pilihan);
            System.out.println(); // Tambahkan baris kosong setelah selesai memproses menu
        } while (pilihan != 0);
    }

    private int getInput() {
        int pilihan = -1;
        do {
            System.out.print("Masukkan pilihan anda: ");
            pilihan = Integer.parseInt(keyboard.nextLine());
            if (pilihan < 0 || pilihan > 7) {
                System.out.println("Pilihan salah! Pilih lagi!");
            }
        } while (pilihan < 0 || pilihan > 7);
        return pilihan;
    }

    private void proses(int pilihan) {
        switch (pilihan) {
            case 0:
                System.out.println("Terima kasih telah menggunakan aplikasi kami!");
                System.exit(0);
                break;
            case 1:
                buatRekening();
                break;
            case 2:
                deposit();
                break;
            case 3:
                tarikTunai();
                break;
            case 4:
                tampilkanDaftarRekening();
                break;
            case 5:
                transferDana();
                break;
            case 6:
                infoSaldo();
                break;
            case 7:
                penutupanRekening();
                break;
        }
    }

    private void buatRekening() {
        System.out.println("\n#### Buat Rekening ####");

        // mengambil data informasi rekening
        tipeRekening = askQuestion("Masukkan tipe akun anda (tabunganku/xpresi): ");
        namaNasabah = askQuestion("Masukkan nama anda: ");
        jumlahDeposit = getDeposit(tipeRekening);

        // membuat rekening baru
        Rekening rekening;
        if (tipeRekening.equalsIgnoreCase("tabunganku")) {
            rekening = new Tabunganku(jumlahDeposit);
        } else {
            rekening = new Xpresi(jumlahDeposit);
        }
        System.out.println("Rekening berhasil dibuat!");
        nasabah = new Nasabah(namaNasabah, rekening);
        bank.tambahNasabah(nasabah);
    }

    private void deposit() {
        System.out.println("\n#### Deposit Tabungan ####");
        int pilihan = pilihRekening();
        if (pilihan >= 0) {
            double nominal = jumlahNominal("Berapa nominal yang ingin anda tabung?: ");
            bank.getNasabah(pilihan).getRekening().deposit(nominal);
            System.out.println("Deposit berhasil!");
        }
    }

    private void tarikTunai() {
        System.out.println("\n#### Tarik Tunai ####");
        int pilihan = pilihRekening();
        if (pilihan >= 0) {
            double nominal = jumlahNominal("Berapa nominal yang ingin anda tarik?: ");
            bank.getNasabah(pilihan).getRekening().tarikTunai(nominal);
            System.out.println("Penarikan tunai berhasil!");
        }
    }

    private void tampilkanDaftarRekening() {
        System.out.println("\n#### Daftar Rekening ####");
        ArrayList<Nasabah> nasabahBank = bank.getNasabahBank();
        if (nasabahBank.size() <= 0) {
            System.out.println("Tidak ada nasabah di Bank!");
            return;
        }
        System.out.println("Daftar rekening nasabah di Bank:");
        for (int i = 0; i < nasabahBank.size(); i++) {
            System.out.println((i + 1) + ") " + nasabahBank.get(i).basicInfo());
        }
    }

    private void transferDana() {
        System.out.println("\n#### Transfer Dana ####");
        int rekeningAsal = pilihRekening();
        if (rekeningAsal >= 0) {
            int rekeningTujuan = pilihRekening();
            if (rekeningTujuan >= 0 && rekeningAsal != rekeningTujuan) {
                double nominal = jumlahNominal("Berapa nominal yang ingin anda transfer?: ");
                bank.transferDana(rekeningAsal, rekeningTujuan, nominal);
                System.out.println("Transfer dana berhasil!");
            } else {
                System.out.println("Pilihan rekening tujuan tidak valid!");
            }
        }
    }

    private void infoSaldo() {
        System.out.println("\n#### Info Saldo ####");
        int pilihan = pilihRekening();
        if (pilihan >= 0) {
            double saldo = bank.getNasabah(pilihan).getRekening().getSaldo();
            System.out.println("Saldo saat ini: Rp " + saldo);
        }
    }

    private void penutupanRekening() {
        System.out.println("\n#### Penutupan Rekening ####");
        int pilihan = pilihRekening();
        if (pilihan >= 0) {
            Nasabah nasabah = bank.getNasabah(pilihan);
            bank.hapusNasabah(nasabah);
            System.out.println("Rekening berhasil ditutup!");
        }
    }

    private double jumlahNominal(String pertanyaan) {
        System.out.print(pertanyaan);
        double nominal = 0;
        try {
            nominal = Double.parseDouble(keyboard.nextLine());
        } catch (NumberFormatException e) {
            nominal = 0;
        }
        return nominal;
    }

    private int pilihRekening() {
        ArrayList<Nasabah> nasabahBank = bank.getNasabahBank();
        if (nasabahBank.size() <= 0) {
            System.out.println("Tidak ada nasabah di Bank!");
            return -1;
        }
        System.out.println("Pilih salah satu akun:");
        for (int i = 0; i < nasabahBank.size(); i++) {
            System.out.println("\t" + (i + 1) + ") " + nasabahBank.get(i).basicInfo());
        }
        int pilihan;
        do {
            System.out.print("Masukkan pilihan anda: ");
            try {
                pilihan = Integer.parseInt(keyboard.nextLine()) - 1;
            } catch (NumberFormatException e) {
                pilihan = -1;
            }
            if (pilihan < 0 || pilihan >= nasabahBank.size()) {
                System.out.println("Pilihan tidak valid! Pilih lagi!");
            }
        } while (pilihan < 0 || pilihan >= nasabahBank.size());
        return pilihan;
    }

    private String askQuestion(String pertanyaan) {
        System.out.print(pertanyaan);
        return keyboard.nextLine();
    }

    private double getDeposit(String tipeRekening) {
        System.out.print("Masukkan jumlah deposit awal: ");
        double deposit = 0;
        try {
            deposit = Double.parseDouble(keyboard.nextLine());
        } catch (NumberFormatException e) {
            deposit = 0;
        }
        return deposit;
    }
}
