package biancso.mevius.nio;

import java.io.Serializable;

@SuppressWarnings("serial")
public class People implements Serializable {
	private final String name;
	private final int age;

	public People(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}
}
