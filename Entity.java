
public class Entity {
	String name;
	String surname;
	String relationship;
	String email;
	String address;
	String phone;
	// Entity attributes.


	public Entity(String name, String surname, String relationship, String phone, String email, String address) {
		this.name = name;
		this.surname = surname;
		this.relationship = relationship;
		this.phone = phone;
		this.email = email;
		this.address = address;
	}	// Constructor method.


	public String toString() {
		String output = "\n\nName: " + name +" "+ surname;
		output += "\nRelationship with Poised: " + relationship;
		output += "\nTelepone Number: " + phone;
		output += "\nEmail: " + email;
		output += "\nPhysical Address: " + address;

		return output;
	}	// toString method.

}
