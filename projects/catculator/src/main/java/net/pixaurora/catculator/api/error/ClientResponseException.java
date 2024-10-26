package net.pixaurora.catculator.api.error;

public class ClientResponseException extends Exception {
    public ClientResponseException(String message) {
        super(message);
    }
}
