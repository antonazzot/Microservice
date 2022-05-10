package resourceservice.service.authservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import resourceservice.model.auth.AppUser;
import resourceservice.model.auth.Role;
import resourceservice.repository.AppUserRepo;
import resourceservice.repository.RoleRepo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final AppUserRepo appUserRepo;
    private final RoleRepo roleRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepo.findAppUserByuserName(username);
        if (appUser==null) {
            log.error("User details error: user with name: " + username + " does not exist ");
            throw new UsernameNotFoundException("User details error: user with name: " + username + " does not exist ");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        appUser.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleName())));
        return new org.springframework.security.core.userdetails.User(appUser.getUserName(), appUser.getPassword(), authorities);
    }

    public List<AppUser> getAllUser () {
     return  appUserRepo.findAll();
    }

   public AppUser saveAppUser (AppUser appUser) {
       appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
       return appUserRepo.save(appUser);
    }

    public  Role saveRole (Role role) {
        return roleRepo.save(role);
    }

    public void addRoleToUser (String username, String roleName) {
        log.info("ADD ROLE: {}", username + " " + roleName);

        AppUser appUserByuserName = appUserRepo.findAppUserByuserName(username);
        appUserByuserName.getRoles().add(roleRepo.findRoleByroleName(roleName));
        appUserRepo.save(appUserByuserName);
    }

    public AppUser getUserByName (String username) {
        return appUserRepo.findAppUserByuserName(username);
    }
}
