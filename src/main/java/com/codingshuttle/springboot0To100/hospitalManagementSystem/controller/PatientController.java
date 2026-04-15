package com.codingshuttle.springboot0To100.hospitalManagementSystem.controller;

import com.codingshuttle.springboot0To100.hospitalManagementSystem.dto.BloodGroupStats;
import com.codingshuttle.springboot0To100.hospitalManagementSystem.entity.Patient;
import com.codingshuttle.springboot0To100.hospitalManagementSystem.repository.PatientRepository;
import com.codingshuttle.springboot0To100.hospitalManagementSystem.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PatientController {

    private final PatientRepository patientRepository;
    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        return ResponseEntity.ok(patientRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        return patientRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        return ResponseEntity.ok(patientRepository.save(patient));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient updatedPatient) {
        return patientRepository.findById(id).map(patient -> {
            patient.setName(updatedPatient.getName());
            patient.setBirthDate(updatedPatient.getBirthDate());
            patient.setEmail(updatedPatient.getEmail());
            patient.setGender(updatedPatient.getGender());
            patient.setBloodGroup(updatedPatient.getBloodGroup());
            return ResponseEntity.ok(patientRepository.save(patient));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/blood-group-stats")
    public ResponseEntity<List<BloodGroupStats>> getBloodGroupStats() {
        return ResponseEntity.ok(patientRepository.getBloodGroupStats());
    }

    @GetMapping("/with-appointments")
    public ResponseEntity<List<Patient>> getPatientsWithAppointments() {
        return ResponseEntity.ok(patientRepository.getAllPatientsWithAppointments());
    }
}
