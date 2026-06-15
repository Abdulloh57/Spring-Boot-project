package library.uz.springbootwithjpa.exception;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(String message) {
        super(message);
    }

    public RecordNotFoundException(String message , Integer id) {
        super(message+" with id"+ id);
    }


}
