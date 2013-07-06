package se.bazookian.monorun.components;

import com.artemis.Component;

public class RandomMovement extends Component {
	public Position previous;
	public Position next;
	
	public float speed;
	public float elapsed;
	
	public RandomMovement() {
		
	}
	
	public RandomMovement(Position previous, Position next, float speed) {
		this.previous = previous;
		this.next = next;
		this.speed = speed;
	}
}