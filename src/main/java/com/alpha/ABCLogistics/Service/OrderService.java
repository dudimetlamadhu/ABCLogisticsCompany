package com.alpha.ABCLogistics.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alpha.ABCLogistics.DTO.LoadingDTO;
import com.alpha.ABCLogistics.DTO.OrderDto;
import com.alpha.ABCLogistics.DTO.ResponseStructure;
import com.alpha.ABCLogistics.DTO.UnLoadingDTO;
import com.alpha.ABCLogistics.Entity.Address;
import com.alpha.ABCLogistics.Entity.Cargo;
import com.alpha.ABCLogistics.Entity.Loading;
import com.alpha.ABCLogistics.Entity.Order;
import com.alpha.ABCLogistics.Entity.Truck;
import com.alpha.ABCLogistics.Entity.Unloading;
import com.alpha.ABCLogistics.Exception.AddressNotFoundException;
import com.alpha.ABCLogistics.Exception.CargoAlreadyExistsException;
import com.alpha.ABCLogistics.Exception.CargoNotSufficentCapacity;
import com.alpha.ABCLogistics.Exception.OrderAlreadyExistsException;
import com.alpha.ABCLogistics.Exception.OrderCannotBeCancelledException;
import com.alpha.ABCLogistics.Exception.OrderNotFoundException;
import com.alpha.ABCLogistics.Exception.TruckNotFoundException;
import com.alpha.ABCLogistics.Repository.AddressRepository;
import com.alpha.ABCLogistics.Repository.CargoService;
import com.alpha.ABCLogistics.Repository.LoadingRepository;
import com.alpha.ABCLogistics.Repository.OrderRepository;
import com.alpha.ABCLogistics.Repository.TruckRepository;
import com.alpha.ABCLogistics.Repository.UnloadingRepository;

import jakarta.validation.Valid;

@Service
public class OrderService {
	@Autowired
	AddressRepository addressRepository;
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	CargoService cargoService;
	@Autowired
	TruckRepository truckRepository;
	@Autowired
	LoadingRepository loadingRepository;  
	@Autowired
	UnloadingRepository unloadingRepository;
	@Autowired
	MailService mailservice;

	/*public ResponseEntity<ResponseStructure<Order>> saveOrder(@Valid OrderDto dto) {
		//order
		Order odd = new Order();
		if(orderRepository.existsById(dto.getId())) {
			throw new OrderAlreadyExistsException("Order with id " + dto.getId() + " already exists");
		}	
		odd.setId(dto.getId());
		odd.setOrderdate(dto.getOrderdate());
		int cost = 10*(dto.getCargoWt()*dto.getCargoCount());
		odd.setCost(cost);
		//cargo
		if(cargoService.existsById(dto.getCargoId())) {
			throw new CargoAlreadyExistsException("Cargo with id " + dto.getCargoId() + " already exists");
		}
		Cargo cargo = new Cargo(dto.getCargoId(), dto.getCargoName(), dto.getCargoDescription(), dto.getCargoWt(), dto.getCargoCount());
		odd.setCargo(cargo);
		//Loading
		Loading load = new Loading();
		if(addressRepository.existsById(dto.getLoadingAddId()) == false) {
		    throw new AddressNotFoundException("Address with id " + dto.getLoadingAddId() + " not found");
		}
		Address loadAdd = addressRepository.findById(dto.getLoadingAddId()).get();
		load.setAddress(loadAdd);

		odd.setLoading(load);
		
		
//		Loading load1 = new Loading();
//		Address loadAdd1 = addressRepository.findById(dto.getLoadingAddId()).orElseThrow(()->new AddressNotFoundException("Address with id " + dto.getLoadingAddId() + " not found"));
//		load1.setAddress(loadAdd1);
//		odd.setLoading(load1);
//		
//		unloading
		Unloading unload = new Unloading();
		Address unloadAdd = addressRepository.findById(dto.getUnloadingAddId()).orElseThrow(()->new AddressNotFoundException("Address with id " + dto.getUnloadingAddId() + " not found"));
		unload.setAddress(unloadAdd);
		odd.setUnloading(unload);
		orderRepository.save(odd);
		//responseStructure
		ResponseStructure<Order> responseStructure = new ResponseStructure<Order>();
		responseStructure.setData(odd);
		responseStructure.setMessage("Order saved successfully");
		responseStructure.setStatuscode(HttpStatus.CREATED.value());
		//mail logic from here
		odd.setMail(dto.getMail());
		String Subject="Order placed";
		String Contact="Your Order Placed From"+odd.getLoading().getAddress().getCity()+"to"+odd.getUnloading().getAddress().getCity()+"For the cost of ₹"+odd.getCost();
		mailservice.sendMail(odd.getMail(), Subject, Contact);
		
		
		return new ResponseEntity<ResponseStructure<Order>>(responseStructure, HttpStatus.CREATED);
	}
	*/
	

	public ResponseEntity<ResponseStructure<Order>> saveOrder(@Valid OrderDto dto) {
	    // order
	    Order odd = new Order();
	    if (orderRepository.existsById(dto.getId())) {
	        throw new OrderAlreadyExistsException("Order with id " + dto.getId() + " already exists");
	    }
	    
	    odd.setId(dto.getId());
	    odd.setOrderdate(dto.getOrderdate());
	    int cost = 10 * (dto.getCargoWt() * dto.getCargoCount());
	    odd.setCost(cost);
	    
	    // cargo
	    // NOTE: This logic seems like it might be better handled as a lookup/update, 
	    // but preserving the INSERT/check-exists pattern as per your original code.
	    if (cargoService.existsById(dto.getCargoId())) {
	        throw new CargoAlreadyExistsException("Cargo with id " + dto.getCargoId() + " already exists");
	    }
	    Cargo cargo = new Cargo(dto.getCargoId(), dto.getCargoName(), dto.getCargoDescription(), dto.getCargoWt(), dto.getCargoCount());
	    odd.setCargo(cargo);

	    // ----------------------------------------------------------------------
	    // CORRECTED LOADING LOGIC: Find existing or create new Loading entity
	    // ----------------------------------------------------------------------
	    
	    // 1. Find the Address entity using Optional/orElseThrow for clean error handling
	    Address loadAdd = addressRepository.findById(dto.getLoadingAddId())
	            .orElseThrow(() -> new AddressNotFoundException("Address with id " + dto.getLoadingAddId() + " not found"));
	    
	    // 2. Find the Loading entity linked to this Address. This uses the findByAddress 
	    //    method (which must be added to LoadingRepository) to respect the @OneToOne/unique constraint.
	 // Check the block again:
	    Loading load = loadingRepository.findByAddress(loadAdd)
	        .orElseGet(() -> {
	            // If not found, create a new Loading entity.
	            Loading newLoad = new Loading();
	            newLoad.setAddress(loadAdd);
	            
	            // This 'return' is mandatory for multiline lambdas
	            return loadingRepository.save(newLoad); 
	        });
	    odd.setLoading(load);
	    
	    // ----------------------------------------------------------------------
	    // CORRECTED UNLOADING LOGIC: Find existing or create new Unloading entity
	    // ----------------------------------------------------------------------
	    
	    Address unloadAdd = addressRepository.findById(dto.getUnloadingAddId())
	            .orElseThrow(() -> new AddressNotFoundException("Address with id " + dto.getUnloadingAddId() + " not found"));

	    // Assuming Unloading also has a @OneToOne relationship with Address and a unique constraint
	 // Corrected logic in your service method

	    Unloading unload = unloadingRepository.findByAddress(unloadAdd) // <-- CRITICAL FIX: use the injected instance (lowercase)
	        .orElseGet(() -> {
	            // If not found, create a new Unloading entity.
	            Unloading newUnload = new Unloading();
	            newUnload.setAddress(unloadAdd);
	            return unloadingRepository.save(newUnload);
	        });
	    odd.setUnloading(unload);
	    
	    // orderRepository.save(odd); - Saving after all entities are prepared
	    
	    // Final save of the Order
	    orderRepository.save(odd);
	    
	    // responseStructure
	    ResponseStructure<Order> responseStructure = new ResponseStructure<Order>();
	    responseStructure.setData(odd);
	    responseStructure.setMessage("Order saved successfully");
	    responseStructure.setStatuscode(HttpStatus.CREATED.value());
	    
	    // mail logic
	    odd.setMail(dto.getMail());
	    String Subject = "Order placed";
	    // Using string concatenation for the body (better to use StringBuilder or String.format)
	    String Contact = "Your Order Placed From " + odd.getLoading().getAddress().getCity() + 
	                     " to " + odd.getUnloading().getAddress().getCity() + 
	                     " For the cost of ₹" + odd.getCost();
	    mailservice.sendMail(odd.getMail(), Subject, Contact);
	    
	    
	
	    return new ResponseEntity<ResponseStructure<Order>>(responseStructure, HttpStatus.CREATED);
	}
	
	

	
	
	//---------------------------------------------
	
	
	
	
	
	

	public ResponseEntity<ResponseStructure<Order>> findOrder(int id) {
		Order order = orderRepository.findById(id).orElseThrow(()->new OrderNotFoundException("Order with id " + id + " not found"));
		ResponseStructure<Order> responseStructure = new ResponseStructure<Order>();
		responseStructure.setData(order);
		responseStructure.setMessage("Order found successfully");
		responseStructure.setStatuscode(HttpStatus.FOUND.value());
		return new ResponseEntity<ResponseStructure<Order>>(responseStructure, HttpStatus.FOUND);
		
	}

	public ResponseEntity<ResponseStructure<Order>> deleteOrder(int id) {
		Order order = orderRepository.findById(id).orElseThrow(()->new OrderNotFoundException("Order with id " + id + " not found"));
		orderRepository.delete(order);
		ResponseStructure<Order> responseStructure = new ResponseStructure<Order>();
		responseStructure.setData(order);
		responseStructure.setMessage("Order deleted successfully");
		responseStructure.setStatuscode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<Order>>(responseStructure, HttpStatus.OK);
		
	}

	public ResponseEntity<ResponseStructure<Order>> updateOrderById(int orderId, int truckId) {
		Order o = orderRepository.findById(orderId).orElseThrow(()->new OrderNotFoundException("Order with id " + orderId + " not found"));
		Truck t=truckRepository.findById(truckId).orElseThrow(()->new TruckNotFoundException());
		
		if(t.getCapacity()>(o.getCargo().getCount())*(o.getCargo().getWeight())){
			o.setCarrier(t.getCarrier());
//			System.out.println(o);
			ResponseStructure<Order> rs=new ResponseStructure<Order>();
			rs.setData(o);
			rs.setMessage("Order Updated Succesfully");
			rs.setStatuscode(HttpStatus.OK.value());
			
			//(admin) updated) mail  for user
			String Subject="Carrier Assigned for Order";
			String Contact="Your Order Assigned for name is"+o.getCarrier().getName()+"his Phone number is"+o.getCarrier().getContact();
			mailservice.sendMail(o.getMail(), Subject, Contact);
			
			
			
			
			
			return new ResponseEntity<ResponseStructure<Order>>(rs, HttpStatus.OK);
					}
		throw new CargoNotSufficentCapacity();
	}

	public ResponseEntity<ResponseStructure<Order>> updateOrderLoading(int orderId, LoadingDTO ldto) {
		Order o=orderRepository.findById(orderId).orElseThrow(()-> new OrderNotFoundException() );
		o.getLoading().setDate(ldto.getDate());
		o.getLoading().setTime(ldto.getTime());
		o.setStatus("pending");
		ResponseStructure<Order> responseStructure = new ResponseStructure<Order>();
		responseStructure.setData(o);
		responseStructure.setMessage("Order Update Loading successfully");
		responseStructure.setStatuscode(HttpStatus.FOUND.value());
		return new ResponseEntity<ResponseStructure<Order>>(responseStructure, HttpStatus.FOUND);
		}

	public ResponseEntity<ResponseStructure<Order>> updateOrderUnLoading(int orderId, UnLoadingDTO uldto) {
		Order o=orderRepository.findById(orderId).orElseThrow(()-> new OrderNotFoundException() );
		o.getLoading().setDate(uldto.getDate());
		o.getLoading().setTime(uldto.getTime());
		o.setStatus("pending");
		ResponseStructure<Order> responseStructure = new ResponseStructure<Order>();
		responseStructure.setData(o);
		responseStructure.setMessage("Order Update Loading successfully");
		responseStructure.setStatuscode(HttpStatus.FOUND.value());
		return new ResponseEntity<ResponseStructure<Order>>(responseStructure, HttpStatus.FOUND);
		
	}

	public ResponseEntity<ResponseStructure<Order>> cancelOrder(int orderid) {
		Order order = findOrder(orderid).getBody().getData();
		Order saved = order;
		if(order.getStatus().equals("placed")) {
			order.setStatus("cancelled");
			saved = orderRepository.save(order);
		}else {
			throw new OrderCannotBeCancelledException();
		}
		ResponseStructure<Order> responseStructure = new ResponseStructure<Order>();
		responseStructure.setData(saved);
		responseStructure.setMessage("Order cancelled successfully");
		responseStructure.setStatuscode(HttpStatus.ACCEPTED.value());
		String subject ="Order Cancelled!";
		String content = "Dear Customer,\n\n"
			    + "We regret to inform you that your order (Order ID: " + saved.getId() + ") "
			    + "has been successfully cancelled as per your request.\n\n"
			    + "If you have any questions or need further assistance, please feel free to contact our customer support team.\n\n"
			    + "Thank you for considering ABC Logistics. We hope to serve you again in the future.\n\n"
			    + "Best regards,\n"
			    + "ABC Logistics Team";
		mailservice.sendMail(saved.getMail(), subject, content);
		return new ResponseEntity<ResponseStructure<Order>>(responseStructure, HttpStatus.ACCEPTED);
	}
		
	}



	



