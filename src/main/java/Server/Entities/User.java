package Server.Entities;

import Server.Entities.StaticEnumerable.Role;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "user")
public class User implements Serializable
{
    public User() {

    }

    public User(String login, String password)
    {
        this.login = login;
        this.password = password;
        this.registrationDate = registrationDate;
    }
    
    public User(String login, String password, Role role, LocalDate registrationDate)
    {
        this.login = login;
        this.password = password;
        this.registrationDate = registrationDate;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "login", length = 50)
    private String login;

    @Column(name = "password", length = 50)
    private String password;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Column(name = "role")
    private Role role;

    public int getAccountValidity()
    {
        return accountValidity;
    }

    public void setAccountValidity(int accountValidity)
    {
        this.accountValidity = accountValidity;
    }

    @Column(name = "account_validity")
    private int accountValidity = 0;
    
    public Role getRole()
    {
        return role;
    }

    public void setRole(Role role)
    {
        this.role = role;
    }

    public LocalDate getRegistrationDate()
    {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate)
    {
        this.registrationDate = registrationDate;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getLogin()
    {
        return login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }
}
