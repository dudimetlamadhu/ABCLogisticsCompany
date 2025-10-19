package com.alpha.ABCLogistics.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alpha.ABCLogistics.Entity.Cargo;
@Repository
public interface CargoService extends JpaRepository<Cargo, Integer> {

}
