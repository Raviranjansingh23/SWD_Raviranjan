package com.taskmaster.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import com.taskmaster.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "epics")
public class Epic {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	private String description;
	private String assignedTeam;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Column(nullable = false)
	private LocalDateTime createdAt = LocalDateTime.now();
	
	public Epic() {
		super();
	}
	
	public Epic(String title, String description, String assignedTeam, Status status) {
		super();
		this.title = title;
		this.description = description;
		this.assignedTeam = assignedTeam;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAssignedTeam() {
		return assignedTeam;
	}

	public void setAssignedTeam(String assignedTeam) {
		this.assignedTeam = assignedTeam;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public int hashCode() {
		return Objects.hash(assignedTeam, description, id, status, title, createdAt);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Epic other = (Epic) obj;
		return Objects.equals(assignedTeam, other.assignedTeam) && Objects.equals(description, other.description)
				&& Objects.equals(id, other.id) && Objects.equals(status, other.status)
				&& Objects.equals(title, other.title) && Objects.equals(createdAt, other.createdAt);
	}

	@Override
	public String toString() {
		return "Epic [id=" + id + ", title=" + title + ", description=" + description + ", assignedTeam=" + assignedTeam
				+ ", status=" + status + ", createdAt=" + createdAt + "]";
	}

}
