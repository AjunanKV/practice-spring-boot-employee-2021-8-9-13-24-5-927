package com.thoughtworks.springbootemployee.Exceptions;

public class NoEmployeeWithIDException extends RuntimeException{

    private String message;

    public NoEmployeeWithIDException(String message) {
       super(message);
    }
//    @Override
//        public String getMessage()
//    {
//        return "No employee with this ID found";
//    }
}
