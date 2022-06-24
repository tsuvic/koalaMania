package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
//フィールド名がUpperCaseの場合、@JsonIgnorePropertiesや@JsonIgnoreでMappingの制御ができない。
//https://stackoverflow.com/questions/30205006/why-does-jackson-2-not-recognize-the-first-capital-letter-if-the-leading-camel-c
// DTOに必要か・・？DAOで直書きで困ることはないのではないか・・？可読性が落ちるのか・・？
//@JsonIgnoreProperties({"TABLE_NAME","COLUMN_POSTIMAGE_ID","COLUMN_POST_ID","COLUMN_ANIMAL_ID","COLUMN_IMAGE_ADDRESS"})
public class PostImage {

	@JsonIgnore
	public final String TABLE_NAME = "post_image";

	@JsonIgnore
	public final String COLUMN_POSTIMAGE_ID = "postimage_id";

	@JsonIgnore
	public final String COLUMN_POST_ID = "post_id";

	@JsonIgnore
	public final String COLUMN_ANIMAL_ID = "animal_id";

	//UpperCaseを変更することで、Jsonとして値を返却しなくなったことを確認済み
	//その他のフィールドに適用するのが手間であるため、FieldのUpperCaseを廃止したい
	@JsonProperty("image_address")
	@JsonIgnore
	public final String COLUMN_IMAGE_ADDRESS = "image_address";

	private int postimage_id;
	private String imageAddress;
	private Animal animal;

	@JsonIgnore
	private Post post;
	private PostImageFavorite postImageFavorite;
}
