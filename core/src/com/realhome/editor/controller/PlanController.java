
package com.realhome.editor.controller;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.realhome.editor.common.Message;
import com.realhome.editor.common.pattern.mvc.BaseController;
import com.realhome.editor.common.pattern.notification.Notification;
import com.realhome.editor.view.PlanView;

public class PlanController extends BaseController<PlanView> {

	public PlanController (PlanView view) {
		super(view);
		view.addListener(new PlanListener());
	}

	@Override
	public void receiveNotification (Notification notification) {
		switch (notification.getName()) {
		case Message.HOUSE_LOADED:
			view.reloadHouse(appModel.getHouse());
			view.enable();
		}
	}

	private class PlanListener extends InputListener {

		@Override
		public boolean scrolled(InputEvent event, float x, float y, int amount) {
			view.zoomCamera(amount*0.2f);
			return true;
		}

		@Override
		public boolean mouseMoved (InputEvent event, float x, float y) {
			view.moveMouse(x, y, false);
			return true;
		}

		@Override
		public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
			view.click(x, y);
			return true;
		}

		@Override
		public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
			view.unclick(x, y);
		}

		@Override
		public void touchDragged(InputEvent event, float x, float y, int pointer) {
			view.moveMouse(x, y, true);
		}
	}
}
