package com.labs.systemdesign.exercise11retry;

/** Stands in for a transient downstream failure (5xx / connection reset). */
public class RemoteException extends RuntimeException {
    public RemoteException(String message) { super(message); }
}
