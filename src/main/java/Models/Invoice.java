package Models;

import Enums.InvoiceStatus;

public class Invoice {
    private long invoiceId;
    private Payment payment;
    private InvoiceStatus invoiceStatus;
    private double tva;
    private double total;
    private double totalTtc;

    // Create a new invoice. The database assigns the invoice ID.
    public Invoice(Payment payment, InvoiceStatus invoiceStatus, double tva, double total) {
        this.payment = payment;
        this.invoiceStatus = invoiceStatus;
        this.tva = tva;
        this.total = total;
        recalculateTotalTtc();
    }

    // Reconstruct an invoice that already has a database ID.
    public Invoice(long invoiceId, Payment payment, InvoiceStatus invoiceStatus,
                   double tva, double total, double totalTtc) {
        this.invoiceId = invoiceId;
        this.payment = payment;
        this.invoiceStatus = invoiceStatus;
        this.tva = tva;
        this.total = total;
        this.totalTtc = totalTtc;
    }

    public long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public InvoiceStatus getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(InvoiceStatus invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public double getTva() {
        return tva;
    }

    public void setTva(double tva) {
        this.tva = tva;
        recalculateTotalTtc();
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
        recalculateTotalTtc();
    }

    public double getTotalTtc() {
        return totalTtc;
    }

    public void setTotalTtc(double totalTtc) {
        this.totalTtc = totalTtc;
    }

    private void recalculateTotalTtc() {
        this.totalTtc = total + (total * tva / 100);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId=" + invoiceId +
                ", payment=" + payment +
                ", invoiceStatus=" + invoiceStatus +
                ", tva=" + tva +
                ", total=" + total +
                ", totalTtc=" + totalTtc +
                '}';
    }
}
