package Models;

import Enums.PaymentMethod;
import Enums.PaymentStatus;

import java.time.LocalDateTime;

public class Payment {

     private String paymentId ;
     private Reservation reservation;
     private double amountPayed ;
     private PaymentMethod paymentMethod;
     private PaymentStatus paymentStatus ;
     private LocalDateTime paymentDate ;

      public Payment(
              String paymentId ,
              Reservation reservation,
              double amountPayed,
              PaymentMethod paymentMethod ,
              PaymentStatus paymentStatus ,
              LocalDateTime paymentDate
              ){
          this.paymentId = paymentId;
          this.reservation = reservation;
          this.amountPayed = amountPayed;
          this.paymentMethod = paymentMethod;
          this.paymentStatus = paymentStatus;
          this.paymentDate = paymentDate;
      }

    public String getPaymentId() {
        return paymentId;
    }


    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public double getAmountPayed() {
        return amountPayed;
    }

    public void setAmountPayed(double amountPayed) {
        this.amountPayed = amountPayed;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }
    @Override
    public String toString() {
        return "Payment{" +
                "paymentId='" + paymentId + '\'' +
                ", reservation=" + reservation +
                ", amountPayed=" + amountPayed +
                ", paymentMethod=" + paymentMethod +
                ", paymentStatus=" + paymentStatus +
                ", paymentDate=" + paymentDate +
                '}';
    }
}
