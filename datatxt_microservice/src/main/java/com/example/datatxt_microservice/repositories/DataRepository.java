package com.example.datatxt_microservice.repositories;

import com.example.datatxt_microservice.entities.DataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface DataRepository extends JpaRepository<DataEntity, Integer> {
    ArrayList<DataEntity> findDataEntitiesByRut(String rut);

    @Query(value = "select * from data as e where e.rut = :rut and e.fecha = :fecha", nativeQuery = true)
    ArrayList<DataEntity> findDataEntitiesByRutAndFecha(@Param("rut") String rut, @Param("fecha") String fecha);
}
