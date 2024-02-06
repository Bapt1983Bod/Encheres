package fr.eni.ecole.encheres.dal;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RetraitDAOImpl implements RetraitDAO {
	
	private final static String CREATE_RETRAIT = "INSERT INTO RETRAITS (no_article, rue, code_postal, ville) values (:idArticle, :rue, :codePostal, :ville)";

	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public RetraitDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public void createRetrait(int idArticle, String rue, String codePostal, String ville) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idArticle", idArticle);
		map.addValue("rue", rue);
		map.addValue("codePostal", codePostal);
		map.addValue("ville", ville);
		
		this.namedParameterJdbcTemplate.update(CREATE_RETRAIT, map);

	}

}
