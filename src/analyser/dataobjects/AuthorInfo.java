package analyser.dataobjects;


public class AuthorInfo {
	private String profileUrl;
	private String city;
	private String state;
	private String country;
	private String gender;
	private String occupation;
	private String interests;
	private String introduction;
	private String favouriteBooks;
	private String favouriteFilms;
	private String favouriteMusic;
	
	public String getFavouriteFilms() {
		return favouriteFilms;
	}
	public void setFavouriteFilms(String favouriteFilms) {
		this.favouriteFilms = favouriteFilms;
	}
	public String getFavouriteMusic() {
		return favouriteMusic;
	}
	public void setFavouriteMusic(String favouriteMusic) {
		this.favouriteMusic = favouriteMusic;
	}

	
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getInterests() {
		return interests;
	}
	public void setInterests(String interests) {
		this.interests = interests;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getFavouriteBooks() {
		return favouriteBooks;
	}
	public void setFavouriteBooks(String favouriteBooks) {
		this.favouriteBooks = favouriteBooks;
	}
	
	public String toString(){
		
		return "City:"+city+"|"+"State:"+state+"|"+"Country:"+country+"|"+"Gender:"+gender+"|"+"Interests:"+interests+"|"+"Introduction:"+introduction+"|"+"Occupation:"+occupation;
	}
	public String getProfileUrl() {
		return profileUrl;
	}
	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

}
