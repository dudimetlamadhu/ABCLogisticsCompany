package com.alpha.ABCLogistics.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alpha.ABCLogistics.DTO.ResponseStructure;
import com.alpha.ABCLogistics.Entity.Carrier;
import com.alpha.ABCLogistics.Entity.Driver;
import com.alpha.ABCLogistics.Entity.Truck;
import com.alpha.ABCLogistics.Exception.CarrierNotFoundException;
import com.alpha.ABCLogistics.Exception.DriverAlreadyPresentException;
import com.alpha.ABCLogistics.Exception.DriverNotFoundException;
import com.alpha.ABCLogistics.Exception.TruckNotFoundException;
import com.alpha.ABCLogistics.Repository.CarrierRepository;
import com.alpha.ABCLogistics.Repository.DriverRepository;
import com.alpha.ABCLogistics.Repository.TruckRepository;

@Service
public class DriverService {
	 @Autowired
	    DriverRepository driverRepository;
	 @Autowired
	 TruckRepository truckRepository;
		@Autowired
		CarrierRepository carrierRepository;
	
	public ResponseEntity<ResponseStructure<Driver>> saveDriver(Driver driver) {
		 Optional<Driver> driverOpt = driverRepository.findById(driver.getId());
	        if (driverOpt.isPresent()) {
	            throw new DriverAlreadyPresentException();
	        }
	        Driver savedDriver = driverRepository.save(driver);

	        ResponseStructure<Driver> responseStructure = new ResponseStructure<>();
	        responseStructure.setData(savedDriver);
	        responseStructure.setMessage("Driver Saved");
	        responseStructure.setStatuscode(HttpStatus.CREATED.value());

	        return new ResponseEntity<>(responseStructure, HttpStatus.CREATED);
		
	}
	public ResponseEntity<ResponseStructure<Driver>> findDriver(int id) {
		Optional<Driver>  driverOpt=driverRepository.findById(id);
		if (driverOpt.isEmpty()) {
            throw new DriverNotFoundException();
        }
		 Driver driver = driverOpt.get();
		 driverRepository.delete(driver);
		 ResponseStructure<Driver> responseStructure = new ResponseStructure<>();
	        responseStructure.setData(driver);
	        responseStructure.setMessage("Driver Deleted");
	        responseStructure.setStatuscode(HttpStatus.ACCEPTED.value());

	        return new ResponseEntity<>(responseStructure, HttpStatus.ACCEPTED);
		
	}
	public ResponseEntity<ResponseStructure<Driver>> deleteDriver(int id) {
        Optional<Driver> driverOpt = driverRepository.findById(id);
        if (driverOpt.isEmpty()) {
            throw new DriverNotFoundException();
        }
        Driver driver = driverOpt.get();
        driverRepository.delete(driver);

        ResponseStructure<Driver> responseStructure = new ResponseStructure<>();
        responseStructure.setData(driver);
        responseStructure.setMessage("Driver Deleted");
        responseStructure.setStatuscode(HttpStatus.ACCEPTED.value());

        return new ResponseEntity<>(responseStructure, HttpStatus.ACCEPTED);

		
	}
	public ResponseEntity<ResponseStructure<Driver>> updateDriverById(int driverid, int carrierid, int truckid) {
		Driver d=driverRepository.findById(driverid).orElseThrow(()->new DriverNotFoundException());
		Truck t=truckRepository.findById(truckid).orElseThrow(()->new TruckNotFoundException());
		Carrier c=carrierRepository.findById(carrierid).orElseThrow(()->new CarrierNotFoundException());
		d.setTruck(t);
		d.setCarrier(c);
		t.setCarrier(c);
		Driver saved= driverRepository.save(d);

        ResponseStructure<Driver> responseStructure = new ResponseStructure<>();
        responseStructure.setData(saved);
        responseStructure.setMessage("Driver Updated");
        responseStructure.setStatuscode(HttpStatus.ACCEPTED.value());

        return new ResponseEntity<>(responseStructure, HttpStatus.ACCEPTED);

		
		
		
	}
	

}
