package com.example.comeonplayerserviceassignment.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class JsonUtils {


    public ResponseEntity<?> responseAsJson(Object object)
    {
        return ResponseEntity.ok(object);
    }

}

