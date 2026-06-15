package com.example.BloqueBoleta.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BloqueBoleta.Model.Boleta;

public interface BoletaRepository extends JpaRepository<Boleta, Integer> {
}
