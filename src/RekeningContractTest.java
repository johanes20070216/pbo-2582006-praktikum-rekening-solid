import java.time.LocalDate;

public class RekeningContractTest {
    public static void main(String[] args) {
        ujiSetor();
        ujiTarik();
        ujiOverdraft();
        ujiBungaDanBiaya();
        ujiDeposito();
    }

    private static void ujiSetor() {
        Rekening rekening = new Tabungan(1, "A", 100_000, 0);
        boolean setorBerhasil = rekening.setor(25_000);
        verifikasi(setorBerhasil && rekening.getSaldo() == 125_000, "Setor positif harus menambah saldo");

        boolean setorNol = rekening.setor(0);
        verifikasi(!setorNol && rekening.getSaldo() == 125_000, "Setor nol harus gagal tanpa mengubah saldo");

        Rekening saldoMaksimal = new Tabungan(8, "H", Long.MAX_VALUE, 0);
        boolean setorMelebihiBatas = saldoMaksimal.setor(1);
        verifikasi(!setorMelebihiBatas && saldoMaksimal.getSaldo() == Long.MAX_VALUE,
                "Setor yang menyebabkan saldo overflow harus gagal");
    }

    private static void ujiTarik() {
        Rekening cukupSaldo = new Tabungan(1, "A", 100_000, 0);
        boolean tarikBerhasil = cukupSaldo.tarik(25_000);
        verifikasi(tarikBerhasil && cukupSaldo.getSaldo() == 75_000, "Tarik dengan saldo cukup harus berhasil");

        Rekening saldoKurang = new Tabungan(2, "B", 10_000, 0);
        boolean tarikSaldoKurang = saldoKurang.tarik(25_000);
        verifikasi(!tarikSaldoKurang && saldoKurang.getSaldo() == 10_000,
                "Tarik melebihi saldo harus gagal tanpa mengubah saldo");
    }

    private static void ujiOverdraft() {
        Rekening giro = new Giro(4, "D", 20_000, 50_000);
        boolean tarikDenganOverdraft = giro.tarik(50_000);
        verifikasi(tarikDenganOverdraft && giro.getSaldo() == -30_000,
                "Giro harus mengizinkan penarikan dalam batas overdraft");

        boolean lewatBatas = giro.tarik(20_001);
        verifikasi(!lewatBatas && giro.getSaldo() == -30_000,
                "Giro harus menolak penarikan yang melewati batas overdraft");
    }

    private static void ujiBungaDanBiaya() {
        Tabungan tabungan = new Tabungan(5, "E", 300_000, 2.0);
        verifikasi(tabungan.hitungBunga() == 6_000, "Bunga tabungan harus sesuai persentase");
        verifikasi(tabungan.biayaAdminBulanan() == 5_000, "Tabungan di bawah saldo minimum dikenai biaya admin");

        Giro giro = new Giro(6, "F", 300_000, 50_000);
        verifikasi(giro.biayaAdminBulanan() == 15_000, "Giro harus dikenai biaya admin bulanan");
    }

    private static void ujiDeposito() {
        Rekening belumJatuhTempo =
                new Deposito(3, "C", 100_000, 4.0, LocalDate.now().plusDays(1));
        boolean tarikDeposito = belumJatuhTempo.tarik(25_000);
        verifikasi(!tarikDeposito && belumJatuhTempo.getSaldo() == 100_000,
                "Tarik deposito sebelum jatuh tempo harus gagal tanpa mengubah saldo");

        Rekening sudahJatuhTempo =
                new Deposito(7, "G", 100_000, 4.0, LocalDate.now().minusDays(1));
        boolean tarikSetelahJatuhTempo = sudahJatuhTempo.tarik(25_000);
        verifikasi(tarikSetelahJatuhTempo && sudahJatuhTempo.getSaldo() == 75_000,
                "Tarik deposito setelah jatuh tempo harus berhasil");
    }

    private static void verifikasi(boolean kondisi, String kasus) {
        assert kondisi : kasus;
        if (!kondisi) {
            throw new AssertionError(kasus);
        }
    }
}
