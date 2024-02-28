package com.shopping.flipkart.util;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageStructure {

    private String to;
    private String subject;
    private String text;
    private Date sentDate;

}
