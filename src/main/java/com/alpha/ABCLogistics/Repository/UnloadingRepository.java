package com.alpha.ABCLogistics.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.ABCLogistics.Entity.Address;
import com.alpha.ABCLogistics.Entity.Unloading;

public interface UnloadingRepository extends JpaRepository<Unloading,Integer> {

    /**
     * Finds an Unloading entity linked to the specified Address.
     * This is necessary to handle the @OneToOne relationship and
     * prevent duplicate key violations on address_id.
     */
	Optional<Unloading> findByAddress(Address address);

}