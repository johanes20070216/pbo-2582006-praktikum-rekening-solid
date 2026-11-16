import java.time.LocalDate;
import java.util.Locale;

public class Deposito extends Rekening implements BerBunga {
    private final double bungaPersen;
    private final LocalDate jatuhTempo;

    public Deposito(int nomor, String pemilik, long saldoAwal, double bungaPersen,
                    LocalDate jatuhTempo) {
        super(nomor, pemilik, saldoAwal);
        if (!Double.isFinite(bungaPersen) || bungaPersen < 0 || jatuhTempo == null) {
            throw new IllegalArgumentException("Data deposito tidak valid");
        }
        this.bungaPersen = bungaPersen;
        this.jatuhTempo = jatuhTempo;
    }

    @Override
    public boolean tarik(long jumlah) {
        return !LocalDate.now().isBefore(jatuhTempo) && super.tarik(jumlah);
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
        return String.format(Locale.US, "Deposito, bunga %.1f%% per bulan", bungaPersen);
    }
}
