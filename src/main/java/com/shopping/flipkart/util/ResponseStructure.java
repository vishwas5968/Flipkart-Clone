package com.shopping.flipkart.util;

import lombok.*;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Component
public class ResponseStructure<T> {
    private T data;
    private String message;
    private int status;
}
