package com.alpha.ABCLogistics.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alpha.ABCLogistics.DTO.ResponseStructure;
import com.alpha.ABCLogistics.DTO.TruckDTO;
import com.alpha.ABCLogistics.Entity.Carrier;
import com.alpha.ABCLogistics.Entity.Truck;
import com.alpha.ABCLogistics.Exception.CarrierNotFoundException;
import com.alpha.ABCLogistics.Exception.TruckAlreadyPresentException;
import com.alpha.ABCLogistics.Exception.TruckNotFoundException;
import com.alpha.ABCLogistics.Repository.CarrierRepository;
import com.alpha.ABCLogistics.Repository.DriverRepository;
import com.alpha.ABCLogistics.Repository.TruckRepository;
@Service
public class TruckService {
@Autowired
TruckRepository truckRepository;
@Autowired
CarrierRepository carrierRepository;
@Autowired
DriverRepository driverRepository;


	public ResponseEntity<ResponseStructure<Truck>> saveTruck(TruckDTO truckdto) {
		Optional<Truck> truckOptinal=truckRepository.findById(truckdto.getId());
		if(truckOptinal.isPresent()) {
			throw new TruckAlreadyPresentException();
		}
		Truck truck =new Truck(); 
		truck.setId(truckdto.getId());
		truck.setName(truckdto.getName());
		truck.setNumber(truckdto.getNumber());
		truck.setCapacity(truckdto.getCapacity());
		truck.setStatus(truckdto.getStatus());
		
		truckRepository.save(truck);
		
		ResponseStructure<Truck> rs=new ResponseStructure<Truck>();
		rs.setData(truck);
		rs.setMessage("Truck is saved!");
		rs.setStatuscode(HttpStatus.CREATED.value());
		return new ResponseEntity<ResponseStructure<Truck>>(rs, HttpStatus.CREATED);
		
		
	}


	public ResponseEntity<ResponseStructure<Truck>> findTruck(int id) {
		Optional<Truck> truckOptinal=truckRepository.findById(id);
		if(!truckOptinal.isPresent()) {
			throw new TruckNotFoundException();
		}

		ResponseStructure<Truck> rs=new ResponseStructure<Truck>();
		Truck truck = truckOptinal.get();
		rs.setData(truck);
		rs.setMessage("Truck Address Found");
		rs.setStatuscode(HttpStatus.FOUND.value());
		return new ResponseEntity<ResponseStructure<Truck>>(rs, HttpStatus.FOUND);
		
	}


	public ResponseEntity<ResponseStructure<Truck>> deleteTruck(int id) {
		Optional<Truck> truckOptinal=truckRepository.findById(id);
		if(!truckOptinal.isPresent()) {
			throw new TruckNotFoundException();
		}
		Truck truck=truckOptinal.get();
		truckRepository.deleteById(id);;
		ResponseStructure<Truck> rs=new ResponseStructure<Truck>();
		rs.setData(truck);
		rs.setMessage("Truck Address deleted");
		rs.setStatuscode(HttpStatus.ACCEPTED.value());
		return new ResponseEntity<ResponseStructure<Truck>>(rs, HttpStatus.ACCEPTED);
		
		
	}


	public ResponseEntity<ResponseStructure<Truck>> UpdateTruck(int TruckId, int CarrierId) {
		Truck t=truckRepository.findById(TruckId).orElseThrow(()-> new TruckNotFoundException());
		Carrier c=carrierRepository.findById(CarrierId).orElseThrow(()-> new CarrierNotFoundException());
		
		 t.setCarrier(c);
		    truckRepository.save(t);
		    ResponseStructure<Truck> rs=new ResponseStructure<Truck>();
			rs.setData(t);
			rs.setMessage("Truck Updated SuccessFully");
			rs.setStatuscode(HttpStatus.ACCEPTED.value());
			return new ResponseEntity<ResponseStructure<Truck>>(rs, HttpStatus.ACCEPTED);
		
	}
	
}
