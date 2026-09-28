package Services;

import Enums.PaymentMethod;
import Models.Payment;
import Models.Reservation;
import PricingRules.EarlyBooking;
import PricingRules.HighSeasionsPricing;
import PricingRules.LongStay;
import PricingRules.LowSeasonsPricing;
import PricingRules.PricingRule;
import PricingRules.WeekEndPricing;
import Stratigies.BankTransferStrategy;
import Stratigies.CashStrategy;
import Stratigies.PaymentStrategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PaymentService {

    private static final double VAT_RATE = 0.20;
    public static class RefundCalculation {
        private final double refundAmount;
        private final double penaltyAmount;
        private final int refundPercentage;

        public RefundCalculation(double refundAmount, double penaltyAmount, int refundPercentage) {
            this.refundAmount = refundAmount;
            this.penaltyAmount = penaltyAmount;
            this.refundPercentage = refundPercentage;
        }

        public double getRefundAmount() {
            return refundAmount;
        }

        public double getPenaltyAmount() {
            return penaltyAmount;
        }

        public int getRefundPercentage() {
            return refundPercentage;
        }
    }
    private final List<PricingRule> pricingRules = List.of(
            new LongStay(),
            new EarlyBooking(),
            new HighSeasionsPricing(),
            new WeekEndPricing(),
            new LowSeasonsPricing()
    );

    public Payment createPayment(Reservation reservation, PaymentMethod paymentMethod) {
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation is required");
        }
        if (paymentMethod == null) {
            throw new IllegalArgumentException("Payment method is required");
        }

        double totalTTC = calculateTotalTTC(reservation.getTotal());
        for (PricingRule rule : pricingRules) {
            totalTTC = rule.apply(reservation, totalTTC);
        }

        PaymentStrategy strategy = switch (paymentMethod) {
            case CASH -> new CashStrategy();
            case BANK_TRANSFER -> new BankTransferStrategy();
        };
        return strategy.createPayment(reservation, totalTTC);
    }

    public RefundCalculation calculateRefund(double amountPaid, LocalDate checkIn, LocalDateTime cancellationTime) {
        if (!Double.isFinite(amountPaid) || amountPaid < 0) {
            throw new IllegalArgumentException("Amount paid must be a non-negative amount");
        }
        if (checkIn == null || cancellationTime == null) {
            throw new IllegalArgumentException("Check-in date and cancellation time are required");
        }

        LocalDateTime checkInTime = checkIn.atStartOfDay();
        int refundPercentage;
        if (cancellationTime.isBefore(checkInTime.minusDays(14))) {
            refundPercentage = 100;
        } else if (cancellationTime.isBefore(checkInTime.minusDays(7))
                || cancellationTime.equals(checkInTime.minusDays(7))) {
            refundPercentage = 70;
        } else if (cancellationTime.isBefore(checkInTime.minusHours(48))
                || cancellationTime.equals(checkInTime.minusHours(48))) {
            refundPercentage = 50;
        } else {
            refundPercentage = 0;
        }

        double refundAmount = amountPaid * refundPercentage / 100;
        refundAmount = Math.round(refundAmount * 100) / 100.0;
        double penaltyAmount = amountPaid - refundAmount;
        penaltyAmount = Math.round(penaltyAmount * 100) / 100.0;

        return new RefundCalculation(refundAmount, penaltyAmount, refundPercentage);
    }

    public double calculateTotalTTC(double totalHt) {
        if (!Double.isFinite(totalHt) || totalHt < 0) {
            throw new IllegalArgumentException("Total before tax must be a non-negative amount");
        }
        return BigDecimal.valueOf(totalHt)
                .multiply(BigDecimal.ONE.add(BigDecimal.valueOf(VAT_RATE)))
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
