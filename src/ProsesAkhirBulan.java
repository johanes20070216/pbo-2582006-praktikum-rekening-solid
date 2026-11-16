import java.text.NumberFormat;
import java.util.Locale;

public class ProsesAkhirBulan {
    private static final NumberFormat RUPIAH = NumberFormat.getInstance(Locale.forLanguageTag("id-ID"));

    public void potongBiayaAdmin(Rekening[] rekening) {
        for (Rekening item : rekening) {
            long biaya = item.biayaAdminBulanan();
            boolean berhasil = biaya == 0 || item.tarik(biaya);
            cetakStatus(item, "biaya admin", biaya, berhasil);
        }
    }

    public void tambahBunga(BerBunga[] rekening) {
        for (BerBunga item : rekening) {
            long bunga = item.hitungBunga();
            item.tambahkanBunga(bunga);
            System.out.printf("%d bunga: Rp%s%n", item.getNomor(), RUPIAH.format(bunga));
        }
    }

    public void autodebet(Rekening[] rekening, int jumlah) {
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah autodebet harus positif");
        }
        for (Rekening item : rekening) {
            boolean berhasil = item.tarik(jumlah);
            cetakStatus(item, "autodebet", jumlah, berhasil);
        }
    }

    private void cetakStatus(Rekening rekening, String transaksi, long jumlah, boolean berhasil) {
        if (berhasil) {
            System.out.printf("%d %s: Rp%s%n", rekening.getNomor(), transaksi, RUPIAH.format(jumlah));
        } else {
            System.out.printf("%d %s: gagal%n", rekening.getNomor(), transaksi);
        }
    }
}
