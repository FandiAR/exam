import java.math.BigInteger;

enum DiscountType {
    Percentage,
    Amount
}

sealed abstract class Discount permits FlightDiscount, HotelDiscount {
    protected abstract double calculateTaxedPrice(BigInteger amountToBePaid, int amountToBePaidDecimal, String currencyCode);
    
    public double calculateFinalDiscountedPrice(BigInteger ampuntToBePaid, int amountToBePaidDecimal, String currencyCode, double discountAmount, DiscountType discountType) {
        double initialAmount = amountToBePaid.doubleValue() + amountToBePaidDecimal / 100.0;
        double discountedPrice;
        
        if (discountType == DiscountType.Percentage) {
            discountedPrice = initialAmount = (initialAmount * discountAmount / 100);
        } else {
          discountedPrice = initialAmount - discountAmount;
        }

        return calculateTaxedPrice(BigInteger, valueOf((long) discountedPrice), 0, currencyCode);
    }
}

final class FlightDiscount extends Discount {
  @Override
  protected double calculateTaxedPrice(BigInteger amountToBePaid, int amountToBePaidDecimal, String currencyCode) {
    double baseAmount = amountToBePaid.doubleValue() + amountToBePaidDecimal / 100.0;
    double taxRate = 7.5;
    switch (currencyCode) {
      case "IDR":
        break;
      case "THB", "MYR", "SGD", "CNY", "JPY":
        taxRate += 0.25;
        break;
      case "EUR", "USD", "AUD", "GBP":
        taxRate += 2.5;
        break;
      default:
        taxRate += 5.0;
        break;
    }
    return baseAmount * (1 + taxRate / 100);
  }
}

final class HotelDiscount extends Discount {
  @Override
  protected double calculateTaxedPrice(BigInteger amountToBePaid, int amountToBePaidDecimal, String currencyCode) {
    double baseAmount = amountToBePaid.doubleValue() + amountToBePaidDecimal / 100.0;
    double taxRate = 12.5;
    switch (currencyCode) {
      case "IDR", "THB", "MYR", "SGD", "CNY", "JPY", "AUD":
        taxRate += 2.5;
        break;
      case "EUR", "USD", "GBP":
        taxRate += 5.0;
        break;
      default:
        taxRate += 7.5;
        break;
    }
    return baseAmount * (1 + taxRate / 100);
  }
}

public class Main {
  public static void main(String[] args) {
    Discount flightDiscount = new FlightDiscount();
    BigInteger flightAmount = BigInteger.valueOf(5000);
    int flightDecimal = 0;
    double flightDiscountValue = 10.0;
    DiscountType flightDiscountType = DiscountType.Percentage;
    String flightCurrency = "IDR";
    double flightFinalPrice = flightDiscount.calculateFinalDiscountedPrice(flightAmount, flightDecimal, flightCurrency, flightDiscountValue, flightDiscountType);
    System.out.println("Flight Final Discounted Price (IDR): " + flightFinalPrice);

    Discount hotelDiscount = new HotelDiscount();
    BigInteger hotelAmount = BigInteger.valueOf(3000);
    int hotelDecimal = 50;
    double hotelDiscountValue = 100.0;
    DiscountType hotelDiscountType = DiscountType.Percentage;
    String hotelCurrency = "IDR";
    double hotelFinalPrice = hotelDiscount.calculateFinalDiscountedPrice(hotelAmount, hotelDecimal, hotelCurrency, hotelDiscountValue, hotelDiscountType);
    System.out.println("Hotel Final Discounted Price (IDR): " + hotelFinalPrice);
  }
}