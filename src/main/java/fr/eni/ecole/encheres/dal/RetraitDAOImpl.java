package fr.eni.ecole.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.ecole.encheres.bo.Retrait;

@Repository
public class RetraitDAOImpl implements RetraitDAO {
	
	private final static String CREATE_RETRAIT = "INSERT INTO RETRAITS (no_article, rue, code_postal, ville) values (:idArticle, :rue, :codePostal, :ville)";
	private final static String DELETE_BY_NOARTICLE = "DELETE FROM RETRAITS WHERE no_article = :noArticle";
	private final static String FIND_BY_ARTICLE = "SELECT rue, code_postal as codePostal, ville FROM RETRAITS WHERE no_article = :noArticle";
	

	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public RetraitDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	// Creation d'un point de retrait
	@Override
	public void createRetrait(int idArticle, String rue, String codePostal, String ville) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idArticle", idArticle);
		map.addValue("rue", rue);
		map.addValue("codePostal", codePostal);
		map.addValue("ville", ville);
		
		this.namedParameterJdbcTemplate.update(CREATE_RETRAIT, map);

	}

	// Recupération d'un point de retrait en fonction du numéro Article
	@Override
	public Retrait readRetrait(int idArticle) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("noArticle", idArticle);
		Retrait retrait = this.namedParameterJdbcTemplate.queryForObject(FIND_BY_ARTICLE, map, new RetraitRowMapper());
		System.out.println("DAO retrait "+ retrait);
		return retrait;
	}

	// Suppression d'un point de retrait en fonction du Numéro Article
	@Override
	public void deleteRetrait(int idArticle) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("noArticle", idArticle);
		
		this.namedParameterJdbcTemplate.update(DELETE_BY_NOARTICLE, map);
	}

}

class RetraitRowMapper implements RowMapper<Retrait> {

	@Override
	public Retrait mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Retrait retrait = new Retrait(rs.getString("rue"), rs.getString("codePostal"), rs.getString("ville"));
		
		return retrait;
	}
	
}
