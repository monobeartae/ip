package app.exceptions;

public class SpecialCharacterException extends MonoBotException {
    private String character = "";

    public SpecialCharacterException(String chara) {
        this.character = chara;
    }

    @Override
    public String getMessage() {
        return "Please don't use'" + this.character + "'! It's my special character, MINE!";
    }
}
