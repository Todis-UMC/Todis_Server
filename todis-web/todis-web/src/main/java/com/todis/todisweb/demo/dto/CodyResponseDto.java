package com.todis.todisweb.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter

public class CodyResponseDto {

    private String topimg;
    private String bottomimg;
    private String shoesimg;
    private String accimg;
    private String topminimg;
    private String bottomminimg;
    private String shoesminimg;
    private String accminimg;
    private Boolean gender;

    public CodyResponseDto(String topimg, String bottomimg, String shoesimg, String accimg,
            String topminimg, String bottomminimg, String shoesminimg, String accminimg,
            Boolean gender) {
        this.topimg = topimg;
        this.bottomimg = bottomimg;
        this.shoesimg = shoesimg;
        this.accimg = accimg;
        this.topminimg = topminimg;
        this.bottomminimg = bottomminimg;
        this.shoesminimg = shoesminimg;
        this.accminimg = accminimg;
        this.gender = gender;
    }
    /*
    public CodyResponseDto(String gender){
        this.gender = gender;
    }
    */

}
