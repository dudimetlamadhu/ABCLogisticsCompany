package com.alpha.ABCLogistics.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alpha.ABCLogistics.Entity.Driver;
@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer>{

}
