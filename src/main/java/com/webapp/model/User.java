package com.webapp.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "users")
public class User {

	@Id
	@Email
	@NotEmpty
	@Column(unique = true)
	private String email;

	@NotEmpty
	@Column(unique = true)
	private String name;

	@Size(min = 4)
	private String password;

	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "user_roles", joinColumns={ // creates a join table called "user_roles" which contains two columns - "user_email", "role_name"
			@JoinColumn(name = "user_email", referencedColumnName = "email") }, inverseJoinColumns = {  // reference the "email" column in user table and use "user_email" alias
					@JoinColumn(name = "role_name", referencedColumnName = "name") }) // reference the "name" column in role table and use "role_name" alias
	private List<Role> roles;


	@OneToMany(
			mappedBy = "user",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private List<Comment> comments = new ArrayList<>();

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public User(String email, String name, String password) {
		this.email = email;
		this.name = name;
		this.password = password;
	}

	public User() {

	}

}
