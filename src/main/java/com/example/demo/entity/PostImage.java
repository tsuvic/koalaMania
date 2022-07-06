package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
//フィールド名がUpperCaseの場合、@JsonIgnorePropertiesや@JsonIgnoreでMappingの制御ができない。
//https://stackoverflow.com/questions/30205006/why-does-jackson-2-not-recognize-the-first-capital-letter-if-the-leading-camel-c
// DTOに必要か・・？DAOで直書きで困ることはないのではないか・・？可読性が落ちるのか・・？
//@JsonIgnoreProperties({"TABLE_NAME","COLUMN_POSTIMAGE_ID","COLUMN_POST_ID","COLUMN_ANIMAL_ID","COLUMN_IMAGE_ADDRESS"})
public class PostImage {
	public final String TABLE_NAME = "post_image";

	public final String COLUMN_POSTIMAGE_ID = "postimage_id";

	public final String COLUMN_POST_ID = "post_id";

	public final String COLUMN_ANIMAL_ID = "animal_id";

	public final String COLUMN_IMAGE_ADDRESS = "image_address";

	private int postimage_id;
	private String imageAddress;
	private Animal animal;

	@JsonIgnore
	private Post post;
	private PostImageFavorite postImageFavorite;
}
