package fr.eni.ecole.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.ecole.encheres.bo.ArticleVendu;

@Repository
public class EncheresDAOImpl implements EncheresDAO {

	private static final String SELECT_BY_ID = "SELECT * FROM ARTICLES_VENDUS WHERE no_article = :no_article";

	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public ArticleVendu findArticleByNoArticle(int noArticle) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("noArticle", noArticle);

		return this.jdbcTemplate.queryForObject(SELECT_BY_ID, params, new ArticleVenduRowMapper());
	}

	private static final class ArticleVenduRowMapper implements RowMapper<ArticleVendu> {
		@Override
		public ArticleVendu mapRow(ResultSet rs, int rowNum) throws SQLException {
			ArticleVendu article = new ArticleVendu();
			article.setNoArticle(rs.getInt("no_article"));
			article.setNomArticle(rs.getString("nom"));
			article.setDescription(rs.getString("description"));

			// mettre d'autres attributs

			return article;
		}
	}
}