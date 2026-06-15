package com.example.BloqueGorro.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BloqueGorro.Model.Gorros;

public interface GorrosRepository extends JpaRepository <Gorros, Integer> {

}
