package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @PostMapping(path = "/myEndpoint", consumes = "application/v1+json", produces = "application/json;version=1")
    public ResponseEntity<MyModelv1> endpointV1(@RequestBody MyModelv1 a) {
        System.out.println("**********V1***********");
        return ResponseEntity.ok(a);
    }

    @PostMapping(path = "/myEndpoint", consumes = {"application/v2+json", "application/json"}, produces = "application/json;version=2")
    public ResponseEntity<MyModelv2> endpointV2(@RequestBody MyModelv2 a) {
        System.out.println("**********V2***********");
        return ResponseEntity.ok(a);
    }
}
