package com.proyecto.spring.models.repository;

import com.proyecto.spring.models.entity.Antropometrico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AntropometricoRepository extends JpaRepository<Antropometrico, Long>{

}
