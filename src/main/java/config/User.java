/*
 * Copyright (c) 2017. Timeline. (http://www.tline.xyz) Gopikrishna V.M.
 */

package config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.dropwizard.jackson.JsonSnakeCase;
import json.ObjectIdDeserializer;
import json.ObjectIdSerializer;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.utils.IndexDirection;

@JsonSnakeCase
@Entity(value = "user", noClassnameStored = true)
public class User {
    @Id
    @JsonSerialize(using = ObjectIdSerializer.class)
    @JsonDeserialize(using = ObjectIdDeserializer.class)
    private ObjectId id;
    private String phone;
    private String name;
    @Indexed(IndexDirection.GEO2D)
    private double[] location = new double[2];

    public User() {
    }

    @JsonCreator
    public User(
            @JsonProperty("id") @JsonSerialize(using = ObjectIdSerializer.class) ObjectId id,
            @JsonProperty("phone") String phone,
            @JsonProperty("name") String name,
            @JsonProperty("location") final double[] location
    ) {
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.location = location;
    }

    public ObjectId getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public double[] getLocation() {
        return location;
    }
}
