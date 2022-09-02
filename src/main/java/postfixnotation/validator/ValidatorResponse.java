package postfixnotation.validator;
     
public class ValidatorResponse {
         
    private StatusCode statusCode;
         
    private String message;

    public ValidatorResponse() {
    }
         
    public ValidatorResponse(StatusCode statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
         
    public StatusCode getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public static ValidatorResponse of(StatusCode statusCode, String message){
        return new ValidatorResponse(statusCode, message);
    }

    public enum StatusCode{
        OKAY,
        ERROR
    }
}
