package com.krishna.marketplace.dto;

import org.springframework.web.multipart.MultipartFile;

public class ProductDto {

    private Long id;

    private String name;

    private Long price;

    private String description;

    private byte[] byteImage;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getByteImage() {
		return byteImage;
	}

	public void setByteImage(byte[] byteImage) {
		this.byteImage = byteImage;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public MultipartFile getImage() {
		return image;
	}

	public String getCatergoryName() {
		return catergoryName;
	}

	public void setCatergoryName(String catergoryName) {
		this.catergoryName = catergoryName;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

	private Long categoryId;

    private MultipartFile image;
    
    private String catergoryName;

}
