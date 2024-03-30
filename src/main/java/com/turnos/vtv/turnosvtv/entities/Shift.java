package com.turnos.vtv.turnosvtv.entities;

import java.util.Date;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.turnos.vtv.turnosvtv.Validations.shiftValidation.IsExistDbShift;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

//Clase turno que tendra cada vehiculo para hacer algún tipo de tramite
@Entity
@Table(name = "shifts", uniqueConstraints = @UniqueConstraint(columnNames = "sku"))
public class Shift {
    
    @Id
    private String id;
    
    @NotEmpty
    @IsExistDbShift
    @Column(name = "sku", unique = true)
    private String sku;
    @NotNull
    private float amount;
   
   @JsonFormat(pattern="yyyy-MM-dd")
   @Temporal(TemporalType.DATE)
    private Date date;
    @NotNull
    private boolean enabled;

    @NotEmpty
    private String patent; 
    
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    @JsonIgnore
    private Vehicle vehicle;
    
    public Shift() {
    }

    public Shift(String id, String sku, float amount, Date date, boolean enabled, String patent, Vehicle vehicle) {
        this.id = id;
        this.sku = sku;
        this.amount = amount;
        this.date = date;
        this.enabled = enabled;
        this.vehicle = vehicle;
        this.patent = patent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    
    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public float getAmount() {
        return amount;
    }
    
    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPatent() {
        return patent;
    }

    public void setPatent(String patent) {
        this.patent = patent;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
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
        Shift other = (Shift) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (Float.floatToIntBits(amount) != Float.floatToIntBits(other.amount))
            return false;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (enabled != other.enabled)
            return false;
        if (vehicle == null) {
            if (other.vehicle != null)
                return false;
        } else if (!vehicle.equals(other.vehicle))
            return false;
        return true;
    }

 

    
    
}