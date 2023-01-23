package com.botscrew.universitymanager.service.impl;

import com.botscrew.universitymanager.dto.LectorDTO;
import com.botscrew.universitymanager.exception.EntityNotExistsException;
import com.botscrew.universitymanager.model.Department;
import com.botscrew.universitymanager.model.Lector;
import com.botscrew.universitymanager.repository.DepartmentRepository;
import com.botscrew.universitymanager.repository.LectorRepository;
import com.botscrew.universitymanager.service.LectorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.botscrew.universitymanager.helper.NullPropertyFinder.getNullPropertyNames;

@Service
public class LectorServiceImpl implements LectorService {

    private final LectorRepository lectorRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public LectorServiceImpl(LectorRepository lectorRepository, DepartmentRepository departmentRepository) {
        this.lectorRepository = lectorRepository;
        this.departmentRepository = departmentRepository;
    }

    @Transactional
    @Override
    public List<Lector> getAllLectors() {
        return lectorRepository.findAll();
    }

    @Override
    public Lector addLector(LectorDTO dto) {
        Lector lector = new Lector();
        BeanUtils.copyProperties(dto, lector);

        if(dto.getDepartmentsIds()!=null){
            lector.setDepartments(findDepartmentsByIds(dto.getDepartmentsIds()));
        }

        return lectorRepository.save(lector);
    }


    @Override
    public Lector getLectorById(String id) {
        return lectorRepository.findById(id).orElseThrow(() ->
                new EntityNotExistsException("Lector with id:" + id + " not found"));
    }

    @Override
    public Lector updateLector(LectorDTO lectorSource, String id) {
        return lectorRepository.findById(id)
                .map(lector -> {
                    BeanUtils.copyProperties(lectorSource, lector, getNullPropertyNames(lectorSource));
                    if(lectorSource.getDepartmentsIds()!=null){
                        lector.setDepartments(findDepartmentsByIds(lectorSource.getDepartmentsIds()));
                    }
                    return lectorRepository.save(lector);
                }).orElseThrow(() ->
                        new EntityNotExistsException("Lector with id:" + id + " not found"));
    }

    @Override
    public void deleteLectorById(String id) {
        lectorRepository.deleteById(id);
    }

    @Override
    public List<Lector> findLectorsByTemplate(String template) {
        return lectorRepository.findLectorsContainsTemplate(template);
    }

    private Set<Department> findDepartmentsByIds(String[] ids) {
        List<String> listOfIds = List.of(ids);
        Set<Department> departments = departmentRepository.findAllById(listOfIds).stream()
                .collect(Collectors.toSet());
        return departments;
    }
}
