package org.fileSorting.utils;

public class CyclicDependencyException extends Exception
{

    /**
     * Constructs a CyclicDependencyException with the specified message.
     *
     * @param message
     */
    public CyclicDependencyException(String message)
    {
        super(message);
    }
}
