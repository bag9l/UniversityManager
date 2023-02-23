package com.botscrew.universitymanager.service.impl;

import com.botscrew.universitymanager.dto.LectorDTO;
import com.botscrew.universitymanager.exception.EntityNotExistsException;
import com.botscrew.universitymanager.mapper.LectorMapper;
import com.botscrew.universitymanager.model.Lector;
import com.botscrew.universitymanager.repository.DepartmentRepository;
import com.botscrew.universitymanager.repository.LectorRepository;
import com.botscrew.universitymanager.service.LectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LectorServiceImpl implements LectorService {

    private final LectorRepository lectorRepository;
    private final DepartmentRepository departmentRepository;
    private final LectorMapper lectorMapper;


    @Transactional
    @Override
    public List<Lector> getAllLectors() {
        return lectorRepository.findAll();
    }

    @Override
    public Lector addLector(LectorDTO dto) {
        Lector lector = lectorMapper.dtoToLector(dto, departmentRepository);

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
                    lector = lectorMapper.dtoToLector(lectorSource, departmentRepository);
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
}
