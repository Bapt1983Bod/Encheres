package fr.eni.ecole.encheres.dal;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.ecole.encheres.bo.Categorie;

@Repository
public class CategorieDAOImpl implements CategorieDAO {
	
	private final static String FIND_ALL = "SELECT * FROM CATEGORIES";

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public CategorieDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	// Récupére depuis le bdd l'ensemble des catégories existantes
	@Override
	public List<Categorie> findAll() {
		
		return this.namedParameterJdbcTemplate.query(FIND_ALL, new BeanPropertyRowMapper<>(Categorie.class));
	}

}
