package com.alpha.ABCLogistics.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.ABCLogistics.Entity.Address;
import com.alpha.ABCLogistics.Entity.Loading;

public interface LoadingRepository extends JpaRepository<Loading, Integer> {

	// ❌ OLD: Optional<Address> findByAddress(Address loadAdd);
	
	// ✅ CORRECTED: The method must return an Optional of the entity type it manages (Loading).
	Optional<Loading> findByAddress(Address address);

}