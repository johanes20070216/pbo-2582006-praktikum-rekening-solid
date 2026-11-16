import java.time.LocalDate;

public class BankSOLID {
    public static void main(String[] args) {
        Tabungan ani = new Tabungan(101, "Ani", 1_000_000, 2.5);
        Tabungan citra = new Tabungan(102, "Citra", 300_000, 2.0);
        Giro budi = new Giro(201, "Budi", 200_000, 500_000);
        Deposito dewi = new Deposito(301, "Dewi", 5_000_000, 4.0, LocalDate.now().plusMonths(6));
        Rekening[] rekening = {
                ani, citra, budi, dewi
        };
        BerBunga[] rekeningBerbunga = {ani, citra, dewi};

        LaporanBank laporan = new LaporanBank();
        ProsesAkhirBulan proses = new ProsesAkhirBulan();

        laporan.cetakDaftar("REKENING AWAL", rekening);
        proses.potongBiayaAdmin(rekening);
        proses.tambahBunga(rekeningBerbunga);
        proses.autodebet(rekening, 50_000);
        laporan.cetakDaftar("REKENING AKHIR", rekening);
        laporan.cetakTotal(rekening);
    }
}
