package com.alchemist.yoru.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
*
* @author Atul Mundaware
* @since 17 04 2023
*/


@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table(name = "role")
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "name")
	private String name;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "permission_role", joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id") }, inverseJoinColumns = {@JoinColumn(name = "permission_id", referencedColumnName = "id") })
	private List<Permission> permissions;
}
