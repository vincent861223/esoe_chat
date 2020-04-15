package container;

import org.json.JSONObject;

public class Container {
	@Override
	public String toString() {
		return new JSONObject(this).toString();
	}
}
