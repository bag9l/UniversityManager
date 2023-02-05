package com.botscrew.universitymanager.repository;

import com.botscrew.universitymanager.model.Lector;
import com.botscrew.universitymanager.repository.custom.CustomizedLectorRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LectorRepository extends JpaRepository<Lector, String>, CustomizedLectorRepository {

    @Query("""
            SELECT l FROM Lector l 
            WHERE 
                LOWER(CONCAT(l.firstname,\" \", l.lastname)) 
                LIKE LOWER(CONCAT('%', :template,'%'))
            """)
    List<Lector> findLectorsContainsTemplate(@Param("template") String template);
}
