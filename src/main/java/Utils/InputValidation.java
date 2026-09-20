package Utils;

public class InputValidation {

    public boolean validateEmpty(String string) {

        if (string == null || string.isBlank()) {
            throw new IllegalArgumentException("This field is required");
        }

        return true;
    }

    public boolean validateNames(String name){

        validateEmpty(name);

        if (name.length() < 3) {
            throw new IllegalArgumentException("Name must be contains 3 letters ");
        }

        if (!name.matches("^[A-Za-zÀ-ÿ ]+$")) {
            throw new IllegalArgumentException("Name can only contain letters");
        }

        return true;
    }

    public boolean validateEmail(String email){

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        validateEmpty(email);

        if(!email.matches(emailRegex)){
            throw new IllegalArgumentException("Email invalid");
        }
        return true;
    }

    public boolean validatePassword(String password){

        validateEmpty(password);

        if(password.length() < 8){
            throw new IllegalArgumentException("password must contains 8 characters at lest");
        }

        return true;
    }


}
