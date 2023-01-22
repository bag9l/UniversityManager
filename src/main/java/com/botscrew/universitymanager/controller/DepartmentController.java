package com.botscrew.universitymanager.controller;

import com.botscrew.universitymanager.dto.DepartmentDTO;
import com.botscrew.universitymanager.model.Department;
import com.botscrew.universitymanager.model.Lector;
import com.botscrew.universitymanager.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping()
    public ResponseEntity<List<Department>> getDepartments() {
        return ResponseEntity.status(HttpStatus.OK).body(
                departmentService.getAllDepartments());
    }

    @GetMapping("{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                departmentService.getDepartmentById(id));
    }

    @PostMapping("create")
    public ResponseEntity<Department> createDepartment(@RequestBody @Valid DepartmentDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(
                departmentService.addDepartment(dto));
    }

    @PutMapping("{id}")
    public ResponseEntity<Department> updateDepartment(@RequestBody @Valid DepartmentDTO dto,
                                                       @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                departmentService.updateDepartment(dto, id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable("id") String id) {
        departmentService.deleteDepartmentById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("{id}/head")
    public ResponseEntity<Lector> getHeadOfDepartment(@PathVariable("id") String departmentId) {
        return ResponseEntity.status(HttpStatus.OK).body(
                departmentService.getHeadByDepartmentId(departmentId));
    }

    @GetMapping("{id}/statistics")
    public ResponseEntity<Map<String, Integer>> getStatistics(@PathVariable("id") String departmentId) {
        return ResponseEntity.status(HttpStatus.OK).body(
                departmentService.getLectorsStatisticOfDepartment(departmentId));
    }
}
