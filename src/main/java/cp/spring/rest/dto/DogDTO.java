package cp.spring.rest.dto;

public class DogDTO {

	private String name;
	private String color;
	private int tail;
	
	public DogDTO() {}

	public DogDTO(String name, String color, int tail) {
		super();
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
		return "DogDTO [name=" + name + ", color=" + color + ", tail=" + tail + "]";
	}

}
