package nowhere132.exceptions;

import lombok.Getter;

@Getter
public class HoldException extends RuntimeException {
    private final String code;

    public HoldException(String code, String message) {
        super(message);
        this.code = code;
    }
}
