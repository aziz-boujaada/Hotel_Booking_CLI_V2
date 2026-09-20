package Utils;

public class MoneyUtil {

    public boolean validateEmpty(String value) {

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("This field is required");
        }

        return true;
    }
    public boolean validatePrices(String price){

       if(!price.matches("^[0-9]+(\\.[0-9]+)?$")){
          throw new IllegalArgumentException("You must be enter a number");
        }

        double parsedPrice = Double.parseDouble(price);
       if(parsedPrice <=  0){
           throw  new IllegalArgumentException("the prices must be greater than 0 ");
       }

       return true ;
    }
}
