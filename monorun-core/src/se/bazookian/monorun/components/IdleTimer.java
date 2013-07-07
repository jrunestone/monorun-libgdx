package se.bazookian.monorun.components;

import com.artemis.Component;
import com.badlogic.gdx.utils.TimeUtils;

public class IdleTimer extends Component {
	public long lastActive;
	public float milliseconds;

	public IdleTimer() {
		lastActive = TimeUtils.millis();
	}
	
	public void update() {
		milliseconds = TimeUtils.millis() - lastActive;
	}
	
	public void reset() {
		lastActive = TimeUtils.millis();
	}
}