package com.thiago.dscatalog.dto;

import java.io.Serializable;

import com.thiago.dscatalog.entities.Role;

public class RoleDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String authority;

	public RoleDTO() {
	}

	public RoleDTO(Role role) {
		this.id = role.getId();
		this.authority = role.getAuthority();
	}

	public RoleDTO(Long id, String authority) {
		super();
		this.id = id;
		this.authority = authority;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoleDTO other = (RoleDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
