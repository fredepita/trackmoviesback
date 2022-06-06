package fr.epita.trackmoviesback.application.security;

import fr.epita.trackmoviesback.domaine.Utilisateur;
import fr.epita.trackmoviesback.infrastructure.utilisateur.UtilisateurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private static Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    /**
     * Recupere le user de la BDD avec ses roles
     * @param identifiant identifiant du user
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(final String identifiant) throws UsernameNotFoundException {
        logger.info("loadUserByUsername(identifiant : " + identifiant + ")");
        final Utilisateur utilisateur = utilisateurRepository.findByIdentifiant(identifiant);
        if (utilisateur == null) {
            throw new UsernameNotFoundException("Identifiant " + identifiant + " non trouvé");
        }
        logger.info("Utilisateur trouvé en BDD : " + utilisateur.getIdentifiant());
        User user = new User(utilisateur.getIdentifiant(), utilisateur.getMotDePasse(), getAuthorities(utilisateur));
        //logger.info("User : " + user.toString() + user.getPassword());

        return user;
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(final Utilisateur utilisateur) {
        logger.info("getAuthorities(Utilisateur : " + utilisateur.toString());
        final String[] userRoles = utilisateur.getRoles().stream().map((role) -> role.name()).toArray(String[]::new);
        final Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
        logger.info("authorities : " + authorities.toString());
        return authorities;
    }

}