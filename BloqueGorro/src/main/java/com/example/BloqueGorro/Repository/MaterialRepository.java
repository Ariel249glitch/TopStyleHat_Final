package com.example.BloqueGorro.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BloqueGorro.Model.Material;

public interface MaterialRepository extends JpaRepository <Material, Integer>{
}
