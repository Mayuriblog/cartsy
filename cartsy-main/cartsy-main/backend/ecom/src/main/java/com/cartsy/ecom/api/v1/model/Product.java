package com.cartsy.ecom.api.v1.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.Date;

@Entity
@Table(name="products")
@JsonInclude(Include.NON_NULL)
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id; 
    @NotBlank()
    @Column(name = "product_name")
    String productName;
	@Column(name = "product_s_desc")
    String productSDesc; 
    @Column(name = "product_l_desc")
    String productLDesc ;
    @Column(name = "product_actual_price")
    int productActualPrice; 
    @Column(name = "product_sale_price")
    int productSalePrice ;
    @Column(name = "product_images")
    String productImages ;
    @Column(name = "seller_id")
    int sellerId ;
    @Column(name = "quantity")
    int quantity ;
    @Column(name = "order_count")
    int orderCount ;
    @Column(name = "color")
    String color;
    @Column(name = "brand")
    String brand ;
    @Column(name = "first_available")
    Date firstAvailable = new Date(System.currentTimeMillis());
    @Column(name = "created_date")
    Date createdDate = new Date(System.currentTimeMillis());;
    @Column(name = "category_id")
    int categoryId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductSDesc() {
		return productSDesc;
	}
	public void setProductSDesc(String productSDesc) {
		this.productSDesc = productSDesc;
	}
	public String getProductLDesc() {
		return productLDesc;
	}
	public void setProductLDesc(String productLDesc) {
		this.productLDesc = productLDesc;
	}
	public int getProductActualPrice() {
		return productActualPrice;
	}
	public void setProductActualPrice(int productActualPrice) {
		this.productActualPrice = productActualPrice;
	}
	public int getProductSalePrice() {
		return productSalePrice;
	}
	public void setProductSalePrice(int productSalePrice) {
		this.productSalePrice = productSalePrice;
	}
	public String getProductImages() {
		return productImages;
	}
	public void setProductImages(String productImages) {
		this.productImages = productImages;
	}
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public Date getFirstAvailable() {
		return firstAvailable;
	}
	public void setFirstAvailable(Date firstAvailable) {
		this.firstAvailable = firstAvailable;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
    
	
}
