package com.shopping.flipkart.util;

import lombok.*;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Component
public class SimpleResponseStructure {

    private int status;
    private String message;

    @Override
    public String toString() {
        return "SimpleResponseStructure{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}