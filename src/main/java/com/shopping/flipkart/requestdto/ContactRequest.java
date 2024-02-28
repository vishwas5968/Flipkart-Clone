package com.shopping.flipkart.requestdto;

import com.shopping.flipkart.enums.Priority;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ContactRequest {

    private String name;
    private long contactNumber;
    private Priority priority;

}
