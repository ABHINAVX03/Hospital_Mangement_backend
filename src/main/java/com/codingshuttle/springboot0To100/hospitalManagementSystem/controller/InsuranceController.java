package com.codingshuttle.springboot0To100.hospitalManagementSystem.controller;

import com.codingshuttle.springboot0To100.hospitalManagementSystem.entity.Insurance;
import com.codingshuttle.springboot0To100.hospitalManagementSystem.entity.Patient;
import com.codingshuttle.springboot0To100.hospitalManagementSystem.repository.InsuranceRepository;
import com.codingshuttle.springboot0To100.hospitalManagementSystem.service.InsuranceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/insurance")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class InsuranceController {

    private final InsuranceRepository insuranceRepository;
    private final InsuranceService insuranceService;

    @GetMapping
    public ResponseEntity<List<Insurance>> getAllInsurances() {
        return ResponseEntity.ok(insuranceRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Insurance> getInsuranceById(@PathVariable Long id) {
        return insuranceRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/insurance/assign?patientId=1
    @PostMapping("/assign")
    public ResponseEntity<Insurance> assignInsurance(
            @RequestBody Insurance insurance,
            @RequestParam Long patientId) {
        Insurance saved = insuranceService.assignInsuranceToPatient(insurance, patientId);
        return ResponseEntity.ok(saved);
    }

    // PUT /api/insurance/update?patientId=1
    @PutMapping("/update")
    public ResponseEntity<Insurance> updateInsurance(
            @RequestBody Insurance insurance,
            @RequestParam Long patientId) {
        Insurance updated = insuranceService.updateInsuranceOfAPatient(insurance, patientId);
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/insurance/remove?patientId=1
    @DeleteMapping("/remove")
    public ResponseEntity<Patient> removeInsurance(@RequestParam Long patientId) {
        Patient patient = insuranceService.removeInsuranceOfAPatient(patientId);
        return ResponseEntity.ok(patient);
    }
}
