package com.turnos.vtv.turnosvtv.entities;

import java.util.HashSet;
import java.util.Set;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.turnos.vtv.turnosvtv.Validations.UpdateValidationGroups;
import com.turnos.vtv.turnosvtv.Validations.userValidation.IsExistDbUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Validated(UpdateValidationGroups.class)
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "dni"), @UniqueConstraint(columnNames = "email")})
public class User {
    
    @Id
    private String id;
    @NotEmpty(groups = UpdateValidationGroups.class)
    private String name;
    @NotEmpty(groups = UpdateValidationGroups.class)
    private String lastname;
   
    @NotEmpty(groups = UpdateValidationGroups.class)
    @IsExistDbUser
    @Column(name = "dni", unique = true)
    private String dni;
    
    @NotNull(groups = UpdateValidationGroups.class)
    private int age;
    @NotEmpty(groups = UpdateValidationGroups.class)
    @Column(name="email", unique = true)
    private String email;
    @NotEmpty(groups = UpdateValidationGroups.class)
    private String password;
    @NotNull(groups = UpdateValidationGroups.class)
    private boolean enabled;
   
    @JsonIgnoreProperties({"users","handler","hibernateLazyInitializer"})
    @ManyToMany
    @JoinTable(
        name = "rolesxusers",
        joinColumns = @JoinColumn(name = "id_user"),
        inverseJoinColumns = @JoinColumn(name = "id_rol"),
        uniqueConstraints = {@UniqueConstraint(columnNames = {"id_user", "id_rol"})}
    )
    private Set<Role> roles;

   @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,  mappedBy = "carOwner")
    private Set<Vehicle> vehicles;

    public User() {
    }

    public User(String id, String name, String lastname, String dni, int age, String email, String password) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.dni = dni;
        this.age = age;
        this.email = email;
        this.password = password;
        this.enabled = true;
        this.roles = new HashSet<>();
        this.vehicles = new HashSet<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicles.add(vehicle);
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (lastname == null) {
            if (other.lastname != null)
                return false;
        } else if (!lastname.equals(other.lastname))
            return false;
        if (age != other.age)
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (enabled != other.enabled)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", lastname=" + lastname + ", dni=" + dni + ", age=" + age
                + ", email=" + email + ", password=" + password + ", enabled=" + enabled + ", roles=" + roles
                + ", vehicles=" + vehicles + "]";
    }



}