package com.alpha.ABCLogistics.DTO;

import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


public class OrderDto {
	private int id;
	private String orderdate;
	private int cargoId;
	private String cargoName;
	private String cargoDescription;
	@Min(value = 1, message = "must be greater than or equal to 1")
	private int cargoCount;

	@Min(value = 1, message = "must be greater than 0")
	private int cargoWt;

	@NotNull
	@JoinColumn(name="loading_id")
	private int loadingAddId;
	@NotNull
	private int unloadingAddId;
	private String Mail;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOrderdate() {
		return orderdate;
	}
	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}
	public int getCargoId() {
		return cargoId;
	}
	public void setCargoId(int cargoId) {
		this.cargoId = cargoId;
	}
	public String getCargoName() {
		return cargoName;
	}
	public void setCargoName(String cargoName) {
		this.cargoName = cargoName;
	}
	public String getCargoDescription() {
		return cargoDescription;
	}
	public void setCargoDescription(String cargoDescription) {
		this.cargoDescription = cargoDescription;
	}
	public int getCargoCount() {
		return cargoCount;
	}
	public void setCargoCount(int cargoCount) {
		this.cargoCount = cargoCount;
	}
	public int getCargoWt() {
		return cargoWt;
	}
	public void setCargoWt(int cargoWt) {
		this.cargoWt = cargoWt;
	}
	public int getLoadingAddId() {
		return loadingAddId;
	}
	public void setLoadingAddId(int loadingAddId) {
		this.loadingAddId = loadingAddId;
	}
	public int getUnloadingAddId() {
		return unloadingAddId;
	}
	public void setUnloadingAddId(int unloadingAddId) {
		this.unloadingAddId = unloadingAddId;
	}
	public String getMail() {
		return Mail;
	}
	public void setMail(String mail) {
		Mail = mail;
	}
	public OrderDto(int id, String orderdate, int cargoId, String cargoName, String cargoDescription,
			@Min(value = 1, message = "must be greater than or equal to 1") int cargoCount,
			@Min(value = 1, message = "must be greater than 0") int cargoWt, @NotNull int loadingAddId,
			@NotNull int unloadingAddId, String mail) {
		super();
		this.id = id;
		this.orderdate = orderdate;
		this.cargoId = cargoId;
		this.cargoName = cargoName;
		this.cargoDescription = cargoDescription;
		this.cargoCount = cargoCount;
		this.cargoWt = cargoWt;
		this.loadingAddId = loadingAddId;
		this.unloadingAddId = unloadingAddId;
		Mail = mail;
	}
	public OrderDto() {
		super();
	}
	
	
	
}
