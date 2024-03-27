package com.turnos.vtv.turnosvtv.entities;

import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.turnos.vtv.turnosvtv.Validations.vehicleValidation.IsExistDbVehicle;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
@Table(name = "vehicles", uniqueConstraints = @UniqueConstraint(columnNames = "patent"))
public class Vehicle {
    
    @Id
    private String id;
    @NotEmpty
    private String type;
    
    @IsExistDbVehicle
    @NotEmpty
    @Column(name="patent", unique = true)
    private String patent;
    
    private int kilometres;
    @NotEmpty
    private String model;
    @NotNull
    private boolean enabled;

    @ManyToOne
    @JoinColumn(name = "car_owner_id")
    @JsonIgnore
    private User carOwner;

   @OneToMany( mappedBy = "vehicle")
    private Set<Shift> shifts;

    
    public Vehicle() {
    }

    public Vehicle(String id, String type, String patent, int kilometres, String model, boolean enabled, User carOwner){
        this.id = id;
        this.type = type;
        this.patent = patent;
        this.kilometres = kilometres;
        this.model = model;
        this.enabled = enabled;
        this.carOwner = carOwner;
       this.shifts = new HashSet<>();
    }

    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPatent() {
        return patent;
    }

    public void setPatent(String patent) {
        this.patent = patent;
    }

    public int getKilometres() {
        return kilometres;
    }

    public void setKilometres(int kilometres) {
        this.kilometres = kilometres;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public User getCarOwner() {
        return carOwner;
    }

    public void setCarOwner(User carOwner) {
        this.carOwner = carOwner;
    }

    public Set<Shift> getShifts() {
        return shifts;
    }

    public void setShifts(Set<Shift> shifts) {
        this.shifts = shifts;
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
        Vehicle other = (Vehicle) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        if (patent == null) {
            if (other.patent != null)
                return false;
        } else if (!patent.equals(other.patent))
            return false;
        if (kilometres != other.kilometres)
            return false;
        if (model == null) {
            if (other.model != null)
                return false;
        } else if (!model.equals(other.model))
            return false;
        if (enabled != other.enabled)
            return false;
       
        return true;
    }

    
}

