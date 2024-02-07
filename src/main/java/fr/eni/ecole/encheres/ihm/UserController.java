//package fr.eni.ecole.encheres.ihm;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//
//public class UserController {
//
//    public Long getCurrentUserId() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        
//        // Vérifie si l'utilisateur est authentifié
//        if (authentication != null && authentication.isAuthenticated()) {
//            // Obtient les détails de l'utilisateur
//            Object principal = authentication.getPrincipal();
//            
//            if (principal instanceof UserDetails) {
//                // Si l'utilisateur est un UserDetails, récupère son ID
//                UserDetails userDetails = (UserDetails) principal;
//                // Ici, assure-toi que l'ID de l'utilisateur est de type Long
//                // Si c'est le cas, retourne l'ID
//                // Remplace "getId()" par la méthode qui récupère l'ID de l'utilisateur dans ton modèle de données
//               return Long.parseLong((userDetails).getNoUtilisateur());
//            }
//        }
//        
//        // Retourne null si l'utilisateur n'est pas authentifié ou si l'ID n'est pas disponible
//        return null;
//    }
//}