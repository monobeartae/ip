package app.events;

/**
 * Callback Handler for InputParser's events
 */
public interface InputParserEventListener {
    public void OnSpecialCharacterErrorEvent(String character);
    public void OnNumberFormatErrorEvent();
    public void OnMissingNumberErrorEvent(String action);
    public void OnErrorEvent(String errorMsg);
}
