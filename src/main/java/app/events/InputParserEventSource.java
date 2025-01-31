package app.events;

import java.util.ArrayList;

/**
 * Callback Invoker for InputParser's events
 */
public class InputParserEventSource {
    protected ArrayList<InputParserEventListener> listeners = null;

    public InputParserEventSource() {
        this.listeners = new ArrayList<>();
    }

    public void AddListener(InputParserEventListener listener) {
        this.listeners.add(listener);
    }
    
    protected void InvokeSpecialCharacterErrorEvent(String character) {
        for (InputParserEventListener listener : listeners) {
            listener.OnSpecialCharacterErrorEvent(character);
        }
    }

    protected void InvokeNumberFormatErrorEvent() {
        for (InputParserEventListener listener : listeners) {
            listener.OnNumberFormatErrorEvent();
        }
    }

    protected void InvokeMissingNumberErrorEvent(String action) {
        for (InputParserEventListener listener : listeners) {
            listener.OnMissingNumberErrorEvent(action);
        }
    }

    protected void InvokeErrorEvent(String errorMsg) {
        for (InputParserEventListener listener : listeners) {
            listener.OnErrorEvent(errorMsg);
        }
    }
}
