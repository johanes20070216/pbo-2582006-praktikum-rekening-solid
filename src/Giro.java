import java.text.NumberFormat;
import java.util.Locale;

public class Giro extends Rekening {
    private final long batasOverdraft;

    public Giro(int nomor, String pemilik, long saldoAwal, long batasOverdraft) {
        super(nomor, pemilik, saldoAwal);
        if (batasOverdraft < 0) {
            throw new IllegalArgumentException("Batas overdraft tidak boleh negatif");
        }
        this.batasOverdraft = batasOverdraft;
    }

    @Override
    public boolean tarik(long jumlah) {
        return tarikDenganBatas(jumlah, batasOverdraft);
    }

    @Override
    public long biayaAdminBulanan() {
        return 15_000;
    }

    @Override
    public String keterangan() {
        return String.format("Giro, batas overdraft Rp%s",
                NumberFormat.getInstance(Locale.forLanguageTag("id-ID")).format(batasOverdraft));
    }
}
