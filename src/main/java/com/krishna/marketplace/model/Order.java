package com.krishna.marketplace.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.krishna.marketplace.dto.OrderDto;
import com.krishna.marketplace.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String orderDescription;

	private Date date;

	private Long amount;

	private String address;

	private String payment;

	private OrderStatus orderStatus;

	private Long totalAmount;

	private Long discount;

	private UUID trackingId;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
	private List<CartItem> cartItems;

	@OneToOne
	@JoinColumn(name = "coupon_id", referencedColumnName = "id")
	private Coupon coupon;

	public OrderDto getOrderDto() {
		OrderDto orderDto = new OrderDto();

		orderDto.setId(this.id);
		orderDto.setOrderDescription(this.orderDescription);
		orderDto.setAddress(this.address);
		orderDto.setTrackingId(this.trackingId);
		orderDto.setAmount(this.amount);
		orderDto.setDate(this.date);
		orderDto.setOrderStatus(this.orderStatus);
		orderDto.setUserName(this.user.getName());

		if (this.coupon != null) {
			orderDto.setCouponName(this.coupon.getName());
		}

		return orderDto;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderDescription() {
		return orderDescription;
	}

	public void setOrderDescription(String orderDescription) {
		this.orderDescription = orderDescription;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long ammount) {
		this.amount = ammount;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Long getDiscount() {
		return discount;
	}

	public void setDiscount(Long discount) {
		this.discount = discount;
	}

	public UUID getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(UUID trackingId) {
		this.trackingId = trackingId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

}
