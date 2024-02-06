package fr.eni.ecole.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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

	private final static String FIND_ALL = "SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, ARTICLES_VENDUS.no_utilisateur, ARTICLES_VENDUS.no_categorie, pseudo, nom, prenom, email, telephone, libelle FROM ARTICLES_VENDUS INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur INNER JOIN CATEGORIES ON ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie where date_debut_encheres <= :dateDuJour and date_fin_encheres >= :dateDuJour;";
	private final static String FIND_BY_CATEGORIE_AND_STRING = "SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, ARTICLES_VENDUS.no_utilisateur, ARTICLES_VENDUS.no_categorie, pseudo, nom, prenom, email, telephone, libelle FROM ARTICLES_VENDUS INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur INNER JOIN CATEGORIES ON ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie where date_debut_encheres <= :dateDuJour and date_fin_encheres >= :dateDuJour nom_article like '%:String%'and no_categorie= :id;";

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public ArticlesDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	// Récupère l'ensemble des articles dans la bbd dont la vente est en cours
	public List<ArticleVendu> findAll(LocalDate date) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("dateDuJour", date);
		List<ArticleVendu> listArticles = this.namedParameterJdbcTemplate.query(FIND_ALL, map, new ArticlesRowMapper());
		return listArticles;
	}

	// Récupère la liste des articles dans la bdd dont la vente est en cours en
	// appliquant le filtre
	@Override
	public List<ArticleVendu> findByCatAndString(LocalDate date, int idCat, String string) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("dateDuJour", date);
		map.addValue("String", string);
		map.addValue("id", idCat);
		return this.namedParameterJdbcTemplate.query(FIND_BY_CATEGORIE_AND_STRING, map, new ArticlesRowMapper());
	}

}

class ArticlesRowMapper implements RowMapper<ArticleVendu> {

	@Override
	public ArticleVendu mapRow(ResultSet rs, int rowNum) throws SQLException {
		Utilisateur utilisateur = new Utilisateur(rs.getInt("no_utilisateur"), rs.getString("pseudo"),
				rs.getString("nom"), rs.getString("prenom"), rs.getString("email"), rs.getString("telephone"));
		Categorie categorie = new Categorie(rs.getInt("no_categorie"), rs.getString("libelle"));
		ArticleVendu article = new ArticleVendu(rs.getInt("no_article"), rs.getString("nom_article"),
				rs.getString("description"), rs.getDate("date_debut_encheres"), rs.getDate("date_fin_encheres"),
				rs.getInt("prix_initial"), rs.getInt("prix_vente"));
		article.setCategorie(categorie);
		article.setUtilisateurV(utilisateur);
		return article;
	}

}