package com.numberone.backend.domain.disaster.controller;

import com.numberone.backend.domain.disaster.dto.request.LatestDisasterRequest;
import com.numberone.backend.domain.disaster.dto.response.LatestDisasterResponse;
import com.numberone.backend.domain.disaster.service.DisasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/disaster")
public class DisasterController {
    private final DisasterService disasterService;
    @PostMapping("/latest")
    public LatestDisasterResponse getLatestDisaster(@RequestBody LatestDisasterRequest latestDisasterRequest){
        return disasterService.getLatestDisaster(latestDisasterRequest);
    }
}
