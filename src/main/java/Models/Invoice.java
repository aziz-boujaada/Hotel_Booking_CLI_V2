package Models;

public class Invoice {
    private Payment payment ;
    private InvoiceStatus invoiceStatus;
    private double tva ;
    private double total;
    private double totalTtc ;


    public Invoice(
            Payment payment,
            InvoiceStatus invoiceStatus,
            double tva,
            double total
    ) {
        this.payment = payment;
        this.invoiceStatus = invoiceStatus;
        this.tva = tva;
        this.total = total;
        this.totalTtc = total + (total * tva / 100);
    }

    public double getTotalTtc() {
        return totalTtc;
    }

    public void setTotalTtc(double totalTtc) {
        this.totalTtc = totalTtc;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTva() {
        return tva;
    }

    public void setTva(double tva) {
        this.tva = tva;
    }

    public InvoiceStatus getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(InvoiceStatus invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }




}
