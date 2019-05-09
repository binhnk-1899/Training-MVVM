
package com.example;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "className",
    "numOfStudent",
    "teacherName"
})
public class Clas {

    @JsonProperty("className")
    private String className;
    @JsonProperty("numOfStudent")
    private Integer numOfStudent;
    @JsonProperty("teacherName")
    private String teacherName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("className")
    public String getClassName() {
        return className;
    }

    @JsonProperty("className")
    public void setClassName(String className) {
        this.className = className;
    }

    @JsonProperty("numOfStudent")
    public Integer getNumOfStudent() {
        return numOfStudent;
    }

    @JsonProperty("numOfStudent")
    public void setNumOfStudent(Integer numOfStudent) {
        this.numOfStudent = numOfStudent;
    }

    @JsonProperty("teacherName")
    public String getTeacherName() {
        return teacherName;
    }

    @JsonProperty("teacherName")
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
