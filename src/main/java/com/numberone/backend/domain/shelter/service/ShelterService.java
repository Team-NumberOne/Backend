package com.numberone.backend.domain.shelter.service;

import com.numberone.backend.domain.shelter.repository.ShelterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShelterService {
    private ShelterRepository shelterRepository;
}
