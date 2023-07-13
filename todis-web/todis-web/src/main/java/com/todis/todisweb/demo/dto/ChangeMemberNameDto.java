package com.todis.todisweb.demo.dto;

public class ChangeMemberNameDto {

    private Long id;

    private String name;

    public ChangeMemberNameDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ChangeMemberNameDto() {
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setNumber(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
