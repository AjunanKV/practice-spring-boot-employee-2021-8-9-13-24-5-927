package com.thoughtworks.springbootemployee.Exceptions;

public class NoEmployeeWithIDException extends RuntimeException{
    @Override
    public String getMessage()
    {
        return "No employee with this ID found";
    }
}
