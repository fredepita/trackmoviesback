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

    @Override
    public UserDetails loadUserByUsername(final String identifiant) throws UsernameNotFoundException {

        logger.debug("loadUserByUsername(identifiant : " + identifiant + ")");

        final Utilisateur utilisateur = utilisateurRepository.findByIdentifiant(identifiant);


        if (utilisateur == null) {
            throw new UsernameNotFoundException("Identifiant " + identifiant + " non trouvé");
        }

        logger.info("Utilisateur trouvé : " + utilisateur.toString());

        User user = new User(utilisateur.getIdentifiant(), utilisateur.getMotDePasse(), getAuthorities(utilisateur));

        logger.debug("User : " + user.toString() + user.getPassword());

        return user;
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(final Utilisateur utilisateur) {

        logger.debug("getAuthorities(Utilisateur : " + utilisateur.toString());

        final String[] userRoles = utilisateur.getRoles().stream().map((role) -> role.name()).toArray(String[]::new);

        logger.debug("Roles {}" + userRoles);

        final Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);

        logger.debug("authorities : " + authorities.toString());

        return authorities;
    }

}
