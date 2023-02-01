package com.diplomaproject.tendersystembackend.service.impls;

import com.diplomaproject.tendersystembackend.model.Role;
import com.diplomaproject.tendersystembackend.model.Users;
import com.diplomaproject.tendersystembackend.model.enums.ERole;
import com.diplomaproject.tendersystembackend.payload.UserPrincipal;
import com.diplomaproject.tendersystembackend.repo.RoleRepository;
import com.diplomaproject.tendersystembackend.repo.UserRepository;
import com.diplomaproject.tendersystembackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service("userDetailsService")
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Users register(String username, String surname, String email, String password) throws UsernameNotFoundException{
        if (userRepository.findByEmail(email).isPresent()){
            throw new UsernameNotFoundException("User is already exist!");
        }

        if (userRepository.findByUsername(username).isPresent()){
            throw new UsernameNotFoundException("User is already exist!");
        }

        Users user = new Users();
        user.setUsername(username);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setActive(true);
        user.setNonBlocked(true);
        Role role = roleRepository.findByRoleType(ERole.ROLE_USER).get();
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
    public Users findUserByName(String username) {
        return userRepository.findByUsername(username)
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


}
