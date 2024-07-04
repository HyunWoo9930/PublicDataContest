package org.example.publicdatacontest.domain.category;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long categoryId;
	private String name;

	@OneToMany(mappedBy = "category")
	private Set<SubCategory> subCategories;
}
