package lib;

public class DFSException extends RuntimeException {
    public DFSException(String message) {
        super(message);
    }
}

class BFSException extends RuntimeException {
    public BFSException(String message) {
        super(message);
    }
}