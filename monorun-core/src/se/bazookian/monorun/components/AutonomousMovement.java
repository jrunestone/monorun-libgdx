package se.bazookian.monorun.components;

import com.artemis.Component;

public class AutonomousMovement extends Component {
	public Position previous;
	public Position next;
	
	public float speed;
	public float elapsed;
	
	public boolean chasePlayer;
	
	public AutonomousMovement() {
		
	}
	
	public AutonomousMovement(Position previous, Position next, float speed) {
		this.previous = previous;
		this.next = next;
		this.speed = speed;
	}
}