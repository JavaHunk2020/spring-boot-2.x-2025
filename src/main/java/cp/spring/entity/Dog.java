package cp.spring.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="dogs_tbl")
public class Dog {
    @Id 
	private String name;
	private String color;
	private int tail;
	
	public Dog() {}

	public Dog(String name, String color, int tail) {
		this.name = name;
		this.color = color;
		this.tail = tail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getTail() {
		return tail;
	}

	public void setTail(int tail) {
		this.tail = tail;
	}

	@Override
	public String toString() {
		return "Dog [name=" + name + ", color=" + color + ", tail=" + tail + "]";
	}

}
