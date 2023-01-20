package com.botscrew.universitymanager.controller;

import com.botscrew.universitymanager.dto.DepartmentDTO;
import com.botscrew.universitymanager.model.Department;
import com.botscrew.universitymanager.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping()
    public ResponseEntity<List<Department>> getDepartments(){
        return ResponseEntity.status(HttpStatus.OK).body(
                departmentService.getAllDepartments());
    }

    @GetMapping("{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable("id") String id){
        return ResponseEntity.status(HttpStatus.OK).body(
                departmentService.getDepartmentById(id));
    }

    @PostMapping("create")
    public ResponseEntity<Department> createDepartment(@RequestBody DepartmentDTO dto){
        return ResponseEntity.status(HttpStatus.OK).body(
                departmentService.addDepartment(dto));
    }

    @PutMapping("{id}")
    public ResponseEntity<Department> updateDepartment(@RequestBody DepartmentDTO dto,
                                                       @PathVariable("id") String id){
        return ResponseEntity.status(HttpStatus.OK).body(
                departmentService.updateDepartment(dto,id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable("id") String id){
        departmentService.deleteDepartmentById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
