package com.sarop.saropbackend.polygon.controller;

import com.sarop.saropbackend.polygon.dto.PolygonAddRequest;
import com.sarop.saropbackend.polygon.service.PolygonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/polygon")
@RequiredArgsConstructor
public class PolygonController {

    private final PolygonService polygonService;
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','OPERATION_ADMIN')")
    public ResponseEntity<?> getPolygonsByMapId(@PathVariable String id){
        return ResponseEntity.ok(polygonService.getAllPolygonsByMapId(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','OPERATION_ADMIN')")
    public ResponseEntity<?> addPolygon(@RequestBody PolygonAddRequest polygonAddRequest){
        return ResponseEntity.ok(polygonService.addPolygon(polygonAddRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','OPERATION_ADMIN')")
    public ResponseEntity<?> deletePolygon(@PathVariable String id){
        polygonService.deletePolygon(id);
        return ResponseEntity.ok().build();
    }
}
