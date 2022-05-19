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
import org.springframework.security.crypto.password.PasswordEncoder;
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
        System.out.println("identifiant : " + identifiant);
        final Utilisateur utilisateur = utilisateurRepository.findByIdentifiant(identifiant);

        System.out.println("Utilisateur après : " + utilisateur.toString());

        if (utilisateur == null) {
            throw new UsernameNotFoundException("Identifiant " + identifiant + " non trouvé");
        }
        logger.debug("Utilisateur trouvé avec identifiant {}", identifiant);
        User user = new User(utilisateur.getIdentifiant(), utilisateur.getMotDePasse(), getAuthorities(utilisateur));
        System.out.println(user.toString() + user.getPassword());
        return user;
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(final Utilisateur utilisateur) {
        final String[] userRoles = utilisateur.getRoles().stream().map((role) -> role.name()).toArray(String[]::new);
        System.out.println("Roles {}" + userRoles);
        final Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
        System.out.println(authorities.toString());
        return authorities;
    }

}
