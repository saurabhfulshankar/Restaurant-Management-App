package com.alchemist.yoru.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

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
@Table(name = "permission")
public class Permission implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "name")
	private String name;

}
