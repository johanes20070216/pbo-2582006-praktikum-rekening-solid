import java.time.LocalDate;

public class RekeningContractTest {
    public static void main(String[] args) {
        Rekening cukupSaldo = new Tabungan(1, "A", 100_000, 0);
        boolean tarikBerhasil = cukupSaldo.tarik(25_000);
        verifikasi(tarikBerhasil && cukupSaldo.getSaldo() == 75_000, "Tarik dengan saldo cukup harus berhasil");

        Rekening saldoKurang = new Tabungan(2, "B", 10_000, 0);
        boolean tarikSaldoKurang = saldoKurang.tarik(25_000);
        verifikasi(!tarikSaldoKurang && saldoKurang.getSaldo() == 10_000,
                "Tarik melebihi saldo harus gagal tanpa mengubah saldo");

        Rekening belumJatuhTempo =
                new Deposito(3, "C", 100_000, 4.0, LocalDate.now().plusDays(1));
        boolean tarikDeposito = belumJatuhTempo.tarik(25_000);
        verifikasi(!tarikDeposito && belumJatuhTempo.getSaldo() == 100_000,
                "Tarik deposito sebelum jatuh tempo harus gagal tanpa mengubah saldo");
    }

    private static void verifikasi(boolean kondisi, String kasus) {
        assert kondisi : kasus;
        if (!kondisi) {
            throw new AssertionError(kasus);
        }
    }
}
