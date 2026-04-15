package com.codingshuttle.springboot0To100.hospitalManagementSystem.controller;

import com.codingshuttle.springboot0To100.hospitalManagementSystem.entity.Department;
import com.codingshuttle.springboot0To100.hospitalManagementSystem.repository.DepartmentRepository;
import com.codingshuttle.springboot0To100.hospitalManagementSystem.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DepartmentController {

    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        return ResponseEntity.ok(departmentRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        return departmentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        return ResponseEntity.ok(departmentRepository.save(department));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody Department updatedDept) {
        return departmentRepository.findById(id).map(dept -> {
            dept.setName(updatedDept.getName());
            if (updatedDept.getHeadDoctor() != null) {
                dept.setHeadDoctor(updatedDept.getHeadDoctor());
            }
            return ResponseEntity.ok(departmentRepository.save(dept));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // POST /api/departments/{deptId}/doctors/{doctorId}
    @PostMapping("/{deptId}/doctors/{doctorId}")
    public ResponseEntity<Department> addDoctorToDepartment(
            @PathVariable Long deptId, @PathVariable Long doctorId) {
        Department dept = departmentRepository.findById(deptId).orElseThrow();
        doctorRepository.findById(doctorId).ifPresent(doc -> dept.getDoctors().add(doc));
        return ResponseEntity.ok(departmentRepository.save(dept));
    }
}
