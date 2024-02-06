package fr.eni.ecole.encheres.dal;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.ecole.encheres.bo.Categorie;

@Repository
public class CategorieDAOImpl implements CategorieDAO {
	
	private final static String FIND_ALL = "SELECT * FROM CATEGORIES";
	private final static String FIND_BY_ID = "SELECT * FROM CATEGORIES where no_categorie = :id";

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public CategorieDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	// Récupére depuis le bdd l'ensemble des catégories existantes
	@Override
	public List<Categorie> findAll() {
		
		return this.namedParameterJdbcTemplate.query(FIND_ALL, new BeanPropertyRowMapper<>(Categorie.class));
	}

	@Override
	public Categorie findById(int id) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("id", id);
		return this.namedParameterJdbcTemplate.queryForObject(FIND_BY_ID, map, new BeanPropertyRowMapper<>(Categorie.class));
	}

}
