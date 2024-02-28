package com.shopping.flipkart.entity;

import com.shopping.flipkart.enums.ImageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "images")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    @Id
    private String imageId;
    private ImageType imageType;
    private byte[] imageBytes;
    private String contentType;
}
