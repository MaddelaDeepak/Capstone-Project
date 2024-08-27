package com.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomErrorDecoder implements ErrorDecoder 
{
    @Override
    public Exception decode(String methodKey, Response response) 
    {
        // You can access the response status, headers, and body here
        HttpStatus status = HttpStatus.valueOf(response.status());
        System.out.println(response.body());
        switch (status) 
        {
            case NOT_FOUND:
                return new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found");
            case BAD_REQUEST:
                return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request");
            default:
                return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }
}

