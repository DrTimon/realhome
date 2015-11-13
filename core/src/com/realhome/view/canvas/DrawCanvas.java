
package com.realhome.view.canvas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class DrawCanvas implements Canvas, Disposable {
	private final float VIRTUAL_HEIGHT = 4f;
	private PolygonSpriteBatch batch;
	private OrthographicCamera cam;
	private boolean enabled;

	public DrawCanvas () {
		create();
	}

	@Override
	public void create () {
		batch = new PolygonSpriteBatch();
		cam = new OrthographicCamera();
	}

	@Override
	public void resize (int width, int height) {
		cam.setToOrtho(false, VIRTUAL_HEIGHT * width / height, VIRTUAL_HEIGHT);
		batch.setProjectionMatrix(cam.combined);
	}

	@Override
	public void render () {
		if (!enabled) return;

		GL20 gl = Gdx.gl;
		gl.glClearColor(1, 1, 1, 1);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	@Override
	public DrawCanvas setEnabled (boolean enabled) {
		this.enabled = enabled;
		return this;
	}
}