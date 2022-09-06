package com.gl.profile.application.profile.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "profile")
public class UserProfile {

	@Transient
    public static final String SEQUENCE_NAME = "profile_sequence";

	@Id
	private long id;
	
	private long user_id;
	
	private String category_id;
	
    private String bio;
    
    private String image_Url;
    
    private String first_name;
    
    private String last_name;
    
    private String phoneno;
    
    public UserProfile() {

    }

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the user_id
	 */
	public long getUser_id() {
		return user_id;
	}

	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	/**
	 * @return the category_id
	 */
	public String getCategory_id() {
		return category_id;
	}

	/**
	 * @param category_id the category_id to set
	 */
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

	/**
	 * @return the bio
	 */
	public String getBio() {
		return bio;
	}

	/**
	 * @param bio the bio to set
	 */
	public void setBio(String bio) {
		this.bio = bio;
	}

	/**
	 * @return the image_Url
	 */
	public String getImage_Url() {
		return image_Url;
	}

	/**
	 * @param image_Url the image_Url to set
	 */
	public void setImage_Url(String image_Url) {
		this.image_Url = image_Url;
	}

	/**
	 * @return the first_name
	 */
	public String getFirst_name() {
		return first_name;
	}

	/**
	 * @param first_name the first_name to set
	 */
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	/**
	 * @return the last_name
	 */
	public String getLast_name() {
		return last_name;
	}

	/**
	 * @param last_name the last_name to set
	 */
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	/**
	 * @return the phoneno
	 */
	public String getPhoneno() {
		return phoneno;
	}

	/**
	 * @param phoneno the phoneno to set
	 */
	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	/**
	 * @return the sequenceName
	 */
	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}

	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserProfile [user_id=" + user_id + ", category_id=" + category_id + ", bio=" + bio + ", image_Url="
				+ image_Url + ", first_name=" + first_name + ", last_name=" + last_name + ", phoneno=" + phoneno + "]";
	}

	
}