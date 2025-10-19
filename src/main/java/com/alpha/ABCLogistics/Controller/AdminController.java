package com.alpha.ABCLogistics.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.ABCLogistics.DTO.LoadingDTO;
import com.alpha.ABCLogistics.DTO.OrderDto;
import com.alpha.ABCLogistics.DTO.ResponseStructure;
import com.alpha.ABCLogistics.DTO.TruckDTO;
import com.alpha.ABCLogistics.DTO.UnLoadingDTO;
import com.alpha.ABCLogistics.Entity.Address;
import com.alpha.ABCLogistics.Entity.Carrier;
import com.alpha.ABCLogistics.Entity.Driver;
import com.alpha.ABCLogistics.Entity.Order;
import com.alpha.ABCLogistics.Entity.Truck;
import com.alpha.ABCLogistics.Service.AddressService;
import com.alpha.ABCLogistics.Service.CarrierService;
import com.alpha.ABCLogistics.Service.DriverService;
import com.alpha.ABCLogistics.Service.OrderService;
import com.alpha.ABCLogistics.Service.TruckService;

import jakarta.persistence.PostUpdate;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	AddressService addressService;
@Autowired
	TruckService truckservice;
@Autowired
CarrierService carrierservice;
@Autowired
DriverService driverService;
@Autowired
OrderService orderService;
	
	@PostMapping("/saveaddress")
	public ResponseEntity<ResponseStructure<Address>> saveAddress(@RequestBody @Valid Address address)
	{
		return addressService.saveAddress(address);
	}
	@GetMapping("/findaddress")
	public ResponseEntity<ResponseStructure<Address>> findAddress(@RequestParam int id) {
		return addressService.findAddress(id);
	}
	@DeleteMapping("/deleteaddress")
	public ResponseEntity<ResponseStructure<Address>> deleteAddress(@RequestParam int id) {
		return addressService.deleteAddress(id);
	}
	
	@PostMapping("/savetruck")
	public ResponseEntity<ResponseStructure<Truck>> Savetruck(@RequestBody TruckDTO truckdto) {
		return truckservice.saveTruck(truckdto);
	}
	@GetMapping("/findtruck")
	public ResponseEntity<ResponseStructure<Truck>> findtruck(@RequestParam int id) {
		return truckservice.findTruck(id);
	}
	@PostMapping("/deletetruck/{id}")
	public ResponseEntity<ResponseStructure<Truck>> deletetruck(@PathVariable int id) {
		return truckservice.deleteTruck(id);
	}
	
	@PostMapping("UpdateTruck/assignCarrier/{TruckId}/{CarrierId}")
	public void UpdateTruck(@PathVariable int TruckId, @PathVariable int CarrierId) {
		truckservice.UpdateTruck(TruckId,CarrierId);
	}
	
	
	@PostMapping("/savecarrier")
	public ResponseEntity<ResponseStructure<Carrier>> findcarrier(@RequestBody Carrier carrier) {
	return	carrierservice.saveCarrier(carrier);
	}
	
	@GetMapping("/findcarrier")
	public ResponseEntity<ResponseStructure<Carrier>> findCarrier(@RequestParam int id) {
		return carrierservice.findCarrier(id);
	}
	
	@DeleteMapping("/deletecarrier")
	public ResponseEntity<ResponseStructure<Carrier>> deleteCarrier(@RequestParam int id) {
		return carrierservice.deleteCarrier(id);
	}
	@PostMapping("/savedriver")
	public ResponseEntity<ResponseStructure<Driver>> saveDriver(@RequestBody Driver driver) {
		return driverService.saveDriver(driver);
	}
	
	@GetMapping("/finddriver")
	public ResponseEntity<ResponseStructure<Driver>> findDriver(@RequestParam int id) {
		return driverService.findDriver(id);
	}
	@DeleteMapping("/deletedriver")
	public ResponseEntity<ResponseStructure<Driver>> deleteDriver(@RequestParam int id) {
		return driverService.deleteDriver(id);
	}
	@PostMapping("/updateDriverById/{driverid}/assignCarrier/{carrierid}/assignTruck/{truckid}")
	public ResponseEntity<ResponseStructure<Driver>> updateDriverById(@PathVariable int driverid,@PathVariable int carrierid,@PathVariable int truckid ) {
		return driverService.updateDriverById(driverid,carrierid,truckid);
		
	}
	

	
	@GetMapping("/findorder")
	public ResponseEntity<ResponseStructure<Order>> findOrder(@RequestParam int id) {
		return orderService.findOrder(id);
	}
	
	@DeleteMapping("/deleteorder")
	public ResponseEntity<ResponseStructure<Order>> deleteOrder(@RequestParam int id) {
		return orderService.deleteOrder(id);
	}
	@PostMapping("/updateOrder/{orderId}/assignCarrier/{truckId}")
	public ResponseEntity<ResponseStructure<Order>> updateOrder(@PathVariable int orderId,@PathVariable int truckId)
	{
		return orderService.updateOrderById(orderId,truckId);
	}
	
	@PostMapping("/updateOrder/{orderId}/updateLoading")
	public ResponseEntity<ResponseStructure<Order>> updateOrderLoading(@PathVariable int orderId,@RequestBody LoadingDTO ldto) {
		return orderService.updateOrderLoading(orderId,ldto);
	}
	@PostMapping("/updateOrder/{orderId}/updateUnLoading")
	public ResponseEntity<ResponseStructure<Order>> updateOrderUnLoading(@PathVariable int orderId,@RequestBody UnLoadingDTO Uldto) {
		return orderService.updateOrderUnLoading(orderId,Uldto);
	}
	
	
	
}
