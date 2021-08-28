package minsu.io.userservice.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


import org.joda.time.DateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Getter @Setter
@Builder
@Document(collection = "User")
@ToString
public class User implements UserDetails {
    @Id
    private String id;

    private static final long serialVersionUID = 1L;

    private List<Role> roles;

    @Indexed(unique = true)
    private String email;

    private Boolean enabled;

    private String passWord;
    private String name;




    private DateTime createdAt;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return this.roles.stream().map(authority -> new SimpleGrantedAuthority(authority.name())).collect(Collectors.toList());
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return passWord;
    }

    @JsonProperty
    public void setPassword(String pwd){
        this.passWord = pwd;
    }


    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled();
    }


//    public User(String email, String pwd, String name, String userId, String encryptedPwd) {
//        this.email = email;
//        this.pwd = pwd;
//        this.name = name;
//        this.userId = userId;
//        this.encryptedPwd = encryptedPwd;
//    }
}
