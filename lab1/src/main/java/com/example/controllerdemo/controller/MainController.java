package com.example.controllerdemo.controller;

import com.example.controllerdemo.model.DataObject;
import com.example.controllerdemo.model.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1")
public class MainController {

    @GetMapping("/data")
    public ResponseEntity<Response> getData() {
        return ResponseEntity.ok(new Response(
                "Data retrieved successfully",
                "Sample data",
                LocalDateTime.now()
        ));
    }

    @PostMapping("/data")
    public ResponseEntity<Response> createData(@RequestBody DataObject data) {
        return ResponseEntity.ok(new Response(
                "Data created successfully",
                data,
                LocalDateTime.now()
        ));
    }

    @PutMapping("/data/{id}")
    public ResponseEntity<Response> updateData(
            @PathVariable Long id,
            @RequestBody DataObject data) {
        data.setId(id);
        return ResponseEntity.ok(new Response(
                "Data updated successfully",
                data,
                LocalDateTime.now()
        ));
    }

    @DeleteMapping("/data/{id}")
    public ResponseEntity<Response> deleteData(@PathVariable Long id) {
        return ResponseEntity.ok(new Response(
                "Data deleted successfully",
                id,
                LocalDateTime.now()
        ));
    }
}