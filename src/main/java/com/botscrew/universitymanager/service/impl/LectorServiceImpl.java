package com.botscrew.universitymanager.service.impl;

import com.botscrew.universitymanager.dto.LectorDTO;
import com.botscrew.universitymanager.exception.EntityNotExistsException;
import com.botscrew.universitymanager.model.Lector;
import com.botscrew.universitymanager.repository.LectorRepository;
import com.botscrew.universitymanager.service.LectorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.botscrew.universitymanager.helper.NullPropertyFinder.getNullPropertyNames;

@Service
public class LectorServiceImpl implements LectorService {

    private final LectorRepository lectorRepository;

    @Autowired
    public LectorServiceImpl(LectorRepository lectorRepository) {
        this.lectorRepository = lectorRepository;
    }

    @Override
    public List<Lector> getAllLectors() {
        return lectorRepository.findAll();
    }

    @Override
    public Lector addLector(LectorDTO dto) {
        Lector lector = new Lector();
        BeanUtils.copyProperties(dto, lector);
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
                    return lectorRepository.save(lector);
                }).orElseThrow(() ->
                        new EntityNotExistsException("Lector with id:" + id + " not found"));
    }

    @Override
    public void deleteLectorById(String id) {
        lectorRepository.deleteById(id);
    }
}
