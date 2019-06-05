package com.example.demo2.slave.model;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.sql.Timestamp;

@MappedSuperclass
public class BaseModel {
	public Timestamp createAt;
	public Timestamp updateAt;
	@PrePersist
	public void setInitValue() {
		createAt = new Timestamp(System.currentTimeMillis());
		updateAt = new Timestamp(System.currentTimeMillis());
	}

	@PreUpdate
	public void setUpdateAt() {
		updateAt = new Timestamp(System.currentTimeMillis());
	}
}
