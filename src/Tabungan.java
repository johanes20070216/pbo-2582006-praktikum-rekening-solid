import java.util.Locale;

public class Tabungan extends Rekening implements BerBunga {
    private final double bungaPersen;

    public Tabungan(int nomor, String pemilik, long saldoAwal, double bungaPersen) {
        super(nomor, pemilik, saldoAwal);
        if (bungaPersen < 0) {
            throw new IllegalArgumentException("Bunga tidak boleh negatif");
        }
        this.bungaPersen = bungaPersen;
    }

    @Override
    public long biayaAdminBulanan() {
        return getSaldo() < 500_000 ? 5_000 : 0;
    }

    @Override
    public long hitungBunga() {
        return Math.round(getSaldo() * bungaPersen / 100.0);
    }

    @Override
    public void tambahkanBunga(long jumlah) {
        tambahkanSaldo(jumlah);
    }

    @Override
    public String keterangan() {
        return String.format(Locale.US, "Tabungan, bunga %.1f%% per bulan", bungaPersen);
    }
}
