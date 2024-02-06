package fr.eni.ecole.encheres.dal;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.ecole.encheres.bo.ArticleVendu;
import fr.eni.ecole.encheres.bo.Categorie;
import fr.eni.ecole.encheres.bo.Utilisateur;

@Repository
public class ArticlesDAOImpl implements ArticlesDAO {

	private final static String FIND_ALL = "SELECT * FROM ARTICLES_VENDUS where date_debut_encheres > :date;";

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public ArticlesDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	public List<ArticleVendu> findAll(Date date) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("date", date);
		return null;

	}

	

}

class ArticlesRowMapper implements RowMapper<ArticleVendu> {

	@Override
	public ArticleVendu mapRow(ResultSet rs, int rowNum) throws SQLException { 
		Utilisateur utilisateur = new Utilisateur(rs.getInt("no_utilisateur"), rs.getString("pseudo"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email"), rs.getString("telephone"));
		Categorie categorie = new Categorie(rs.getInt("no_categorie"), rs.getString("libelle"));
		ArticleVendu article = new ArticleVendu(rs.getInt("no_article"), rs.getString("nom_article"), rs.getString("description"), rs.getDate("date_debut_encheres"), rs.getDate("date_fin_encheres"), rs.getInt("prix_initial"), rs.getInt("prix_vente"));
		article.setCategorie(categorie);
		article.setUtilisateurV(utilisateur);
		return article;
	}
	
}