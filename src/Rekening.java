public abstract class Rekening {
    private final int nomor;
    private final String pemilik;
    private long saldo;

    protected Rekening(int nomor, String pemilik, long saldoAwal) {
        if (nomor <= 0 || pemilik == null || pemilik.isBlank() || saldoAwal < 0) {
            throw new IllegalArgumentException("Data rekening tidak valid");
        }
        this.nomor = nomor;
        this.pemilik = pemilik;
        this.saldo = saldoAwal;
    }

    public int getNomor() {
        return nomor;
    }

    public String getPemilik() {
        return pemilik;
    }

    public long getSaldo() {
        return saldo;
    }

    public boolean setor(long jumlah) {
        if (jumlah <= 0 || saldo > Long.MAX_VALUE - jumlah) {
            return false;
        }
        saldo += jumlah;
        return true;
    }

    public boolean tarik(long jumlah) {
        return tarikDenganBatas(jumlah, 0);
    }

    protected boolean tarikDenganBatas(long jumlah, long batasTambahan) {
        if (jumlah <= 0 || batasTambahan < 0) {
            return false;
        }

        long saldoMaksimal;
        try {
            saldoMaksimal = Math.addExact(saldo, batasTambahan);
        } catch (ArithmeticException e) {
            saldoMaksimal = Long.MAX_VALUE;
        }
        if (jumlah > saldoMaksimal) {
            return false;
        }

        try {
            saldo = Math.subtractExact(saldo, jumlah);
        } catch (ArithmeticException e) {
            return false;
        }
        return true;
    }

    public long biayaAdminBulanan() {
        return 0;
    }

    protected void tambahkanSaldo(long jumlah) {
        if (jumlah < 0) {
            throw new IllegalArgumentException("Jumlah tidak boleh negatif");
        }
        saldo = Math.addExact(saldo, jumlah);
    }

    public abstract String keterangan();
}
