class BadRequestError extends Exception {
    public BadRequestError(String msg) {
        super(msg);
    }
}
class UnprocessableEntityError extends Exception {
    public UnprocessableEntityError(String msg) {
        super(msg);
    }
}
interface Validator {
    public void validate(Object input) throws BadRequestError, UnprocessableEntityError;
}

class PriceValidatorService implements Validator {
    @Override
    public void validate(Object price) throws BadRequestError, UnprocessableEntityError {
        if (price instanceof String) {
            try {
                Double.parseDouble((String) price);
            } catch (NumberFormatException e) {
                throw new UnprocessableEntityError("Incorrect input format(price)");
            }
        }
        if (price instanceof Integer) {
            Integer integerPrice = (Integer) price;
            if (integerPrice <= 0) {
                throw new BadRequestError("Amount can not be zero or negative");
            }
        } else if (price instanceof Double) {
            Double doublePrice = (Double) price;
                if (doublePrice <= 0.0) {
                    throw new BadRequestError("Amount can not be zero or negative");
                }
        } else if (price instanceof String) {
            try {
                Double convertedPrice = Double.parseDouble((String) price);
                if (convertedPrice <= 0.0) {
                    throw new BadRequestError("Amount can not be zero or negative");
                }
            } catch (NumberFormatException e) {
                throw new UnprocessableEntityError("Incorrect input format (price)");
            }
        } else {
            throw new UnprocessableEntityError("Incorrect input format (price)");
        }
    }
}

class CurrencyValidatorService implements Validator {
    private static final String[] VALID_CURRENCY_CODES_ENUM = {"IDR", "USD", "SGD", "EUR", "MYR", "THB", "CNY", "JPY", "AUD"};
    
    @Override
    public void validate(Object currency) throws BadRequestError, UnprocessableEntityError {
        if (currency instanceof String) {
            String strCurrency = (String) currency;
            if (strCurrency.length() != 3) {
                throw new UnprocessableEntityError("Incorrect input format (currency)");
            }
            boolean isValidCurrency = false;
            for (String validCode: VALID_CURRENCY_CODES_ENUM) {
                if (validCode.equals(strCurrency)) {
                    isValidCurrency = true;
                    break;
                }
            }
            if (!isValidCurrency) {
                throw new UnprocessableEntityError("Currency code is not registered");
            } else {
                throw new UnprocessableEntityError("Incorrect input format (currency)");
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Validator priceValidator = new PriceValidatorService();
        Validator currencyValidator = new CurrencyValidatorService();
        
        try {
            priceValidator.validate(-10);
        } catch (Exception e) {
            System.out.println("PriceValidator Error: " + e.getMessage());
        }
        
        try {
            currencyValidator.validate("XYZ");
        } catch (Exception e) {
            System.out.println("CurrencyValidator Error: " + e.getMessage());
        }
    }
}