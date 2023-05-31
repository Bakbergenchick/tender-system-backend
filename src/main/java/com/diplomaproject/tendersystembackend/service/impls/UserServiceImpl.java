package com.diplomaproject.tendersystembackend.service.impls;

import com.diplomaproject.tendersystembackend.model.Role;
import com.diplomaproject.tendersystembackend.model.Users;
import com.diplomaproject.tendersystembackend.model.VerificationToken;
import com.diplomaproject.tendersystembackend.model.enums.ERole;
import com.diplomaproject.tendersystembackend.model.enums.Tariff;
import com.diplomaproject.tendersystembackend.payload.UserPrincipal;
import com.diplomaproject.tendersystembackend.repo.RoleRepository;
import com.diplomaproject.tendersystembackend.repo.UserRepository;
import com.diplomaproject.tendersystembackend.repo.VerificationTokenRepository;
import com.diplomaproject.tendersystembackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@Service("userDetailsService")
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository tokenRepository;

    @Override
    public Users register(String firstname, String surname, String patronymic, String email, String password) throws UsernameNotFoundException{
        if (userRepository.findByEmail(email).isPresent()){
            throw new UsernameNotFoundException("User is already exist!");
        }

        Users user = new Users();
        user.setFirstname(firstname);
        user.setSurname(surname);
        user.setPatronymic(patronymic);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setTariff(Tariff.NONE);
//        user.setActive(true);
        user.setNonBlocked(true);
        Role role = roleRepository.findByRoleType(ERole.ROLE_EXECUTOR).get();
        user.setRoles(Collections.singleton(role));

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User doesnt't exist!"));

        return new UserPrincipal(user);
    }


    @Override
    public Users findUserByName(String firstname) {
        return userRepository.findByFirstname(firstname)
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exist!"));
    }

    @Override
    public Users findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User is already exist!"));
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void saveUserVerificationToken(Users theUsers, String token) {
        var verificationToken = new VerificationToken(token, theUsers);
        tokenRepository.save(verificationToken);
    }

    @Override
    public String validateToken(String theToken) {
        VerificationToken token = tokenRepository.findByToken(theToken);
        if(token == null){
            return "Invalid verification token";
        }
        Users users = token.getUsers();
        Calendar calendar = Calendar.getInstance();
        if ((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
            tokenRepository.delete(token);
            return "Token already expired";
        }
        users.setActive(true);
        userRepository.save(users);
        return "valid";
    }


}
