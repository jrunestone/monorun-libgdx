package se.bazookian.monorun.components;

import com.artemis.Component;

public class Sprite extends Component {
	public String name;
	public float scale;
	
	public Sprite() {
		
	}
	
	public Sprite(String name) {
		this.name = name;
		scale = 1;
	}
	
	public Sprite(String name, float scale) {
		this(name);
		this.scale = scale;
	}
}