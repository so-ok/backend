package com.sook.backend.user.model;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.sook.backend.common.model.BaseModel;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sook_user", indexes = {
	@Index(name = "sook_user_username_index", columnList = "username")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class User extends BaseModel {
	@Serial
	private static final long serialVersionUID = 1L;

	@Column(name = "user_id", unique = true, nullable = false)
	private String userId;

	@Column(name = "username", nullable = false)
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@Column(name = "image")
	private String image;

	@Column(name = "oauth_provider", nullable = false)
	private String oauthProvider;

	@Builder.Default
	private boolean enabled = true;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "sook_user_roles")
	@Enumerated(EnumType.STRING)
	@Builder.Default
	private Set<Role> roles = new HashSet<>();

	private LocalDateTime lastLogin;

	public void updateImage(String imageUrl) {
		image = imageUrl;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles
			.stream()
			.map(Role::key)
			.map(SimpleGrantedAuthority::new)
			.collect(Collectors.toList());
	}
}
