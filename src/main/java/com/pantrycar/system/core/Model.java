package com.pantrycar.system.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by kunal.agarwal on 04/04/15.
 */
@MappedSuperclass
@Getter
@Setter
public abstract class Model {
    private static final Logger logger = LoggerFactory.getLogger(Model.class);

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected int id;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    @Column(name = "created_at", nullable = true)
    protected Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    @Column(name = "updated_at", nullable = true)
    protected Date updatedAt;

    public <T extends Model> T updateTimeStamps() {
        Date date = new Date();
        if (this.getCreatedAt() == null)
            this.setCreatedAt(date);
        this.setUpdatedAt(date);
        return (T) this;
    }
}

