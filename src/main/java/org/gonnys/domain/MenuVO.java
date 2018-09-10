package org.gonnys.domain;

import lombok.Data;

import java.util.Date;
@Data
public class MenuVO {
    private int mno, price;
    private String mname, img;
    private Date regdate;
}
