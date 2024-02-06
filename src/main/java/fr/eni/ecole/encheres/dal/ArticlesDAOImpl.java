package fr.eni.ecole.encheres.dal;

import java.util.List;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.ecole.encheres.bo.ArticleVendu;

@Repository
public class ArticlesDAOImpl implements ArticlesDAO {
	
	private final static String FIND_ALL = "";
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public List<ArticleVendu> findAll(){
		return null;
		
	}

}
