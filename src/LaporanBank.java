import java.text.NumberFormat;
import java.util.Locale;

public class LaporanBank {
    private static final NumberFormat RUPIAH = NumberFormat.getInstance(Locale.forLanguageTag("id-ID"));

    public void cetakDaftar(String judul, Rekening[] rekening) {
        System.out.printf("%n========== %s ==========%n", judul);
        for (Rekening item : rekening) {
            System.out.printf("%d | %-8s | Rp%s%n", item.getNomor(), item.getPemilik(),
                    RUPIAH.format(item.getSaldo()));
            System.out.printf("    %s%n", item.keterangan());
        }
    }

    public void cetakTotal(Rekening[] rekening) {
        long total = 0;
        for (Rekening item : rekening) {
            total += item.getSaldo();
        }
        System.out.printf("Total dana bank: Rp%s%n", RUPIAH.format(total));
    }
}
