
package com.realhome.view.canvas;

import com.badlogic.gdx.utils.Disposable;
import com.realhome.data.House;

public interface Canvas extends Disposable {
	public void create ();

	public void resize (int width, int height);

	public void render ();

	public void reload (House house);

	public Canvas setEnabled (boolean enabled);
}
