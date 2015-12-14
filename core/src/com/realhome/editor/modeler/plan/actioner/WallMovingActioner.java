package com.realhome.editor.modeler.plan.actioner;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.realhome.editor.model.Point;
import com.realhome.editor.model.house.Wall;
import com.realhome.editor.modeler.plan.model.HousePlan;
import com.realhome.editor.modeler.plan.model.WallPlan;

public class WallMovingActioner implements Actioner {
	private HousePlan house;
	private Point tmp = new Point();
	private Vector2 lastLocation = new Vector2();
	private Array<Point> tmpPoints = new Array<Point>();

	@Override
	public Actioner init (HousePlan house) {
		this.house = house;
		return this;
	}

	@Override
	public int move (int x, int y) {
		if(house.getSelectedWall() == null)
			return Action.TYPE_EMPTY;

		int dx = (int)lastLocation.x - x;
		int dy = (int)lastLocation.y - y;
		moveWallDelta(-dx, -dy);
		lastLocation.set(x, y);

		return Action.TYPE_MOVE_WALL;
	}

	/**
	 * Move selected wall with delta values in params
	 * Loop through all walls to find adjacent walls
	 */
	private void moveWallDelta(int x, int y) {
		tmpPoints.clear();
		tmpPoints.addAll(house.getSelectedWall().getOrigin().getPoints());

		for(int i = 0; i < house.getWalls().size; i++) {
			Wall w1 = house.getWalls().get(i).getOrigin();
			Wall w2 = house.getSelectedWall().getOrigin();
			
			if (w1 != w2) {
				System.out.println(w1);
				Point contactPoint = w1.getLinkedPoint(w2);
				if(contactPoint != null) {
					tmpPoints.add(contactPoint);
				}
			}
		}

		System.out.println("++++++++");
		for(Point p : tmpPoints) {
			//System.out.println(p);
			p.add(x, y);
			//System.out.println(p);
			//System.out.println("-------");
		}
	}

	@Override
	public int click (int x, int y) {
		tmp.set(x, y);
		lastLocation.set(x, y);

		for(WallPlan wall : house.getWalls()) {
			if(pointInWall(wall, tmp)){
				house.setSelectedWall(wall);
				return Action.TYPE_SELECT_WALL;
			}
		}

		return Action.TYPE_EMPTY;
	}

	private boolean pointInWall(WallPlan wall, Point point) {
		Point[] points = wall.getPoints();
		if(Intersector.isPointInTriangle(
			point.x, point.y,
			points[0].x, points[0].y,
			points[1].x, points[1].y,
			points[2].x, points[2].y))
			return true;
		if(Intersector.isPointInTriangle(
			point.x, point.y,
			points[2].x, points[2].y,
			points[1].x, points[1].y,
			points[3].x, points[3].y))
			return true;
		return false;
	}

	@Override
	public int unclick (int x, int y) {
		if(house.getSelectedWall() != null) {
			house.setSelectedWall(null);
			return Action.TYPE_UNSELECT_WALL;
		}

		return Action.TYPE_EMPTY;
	}
}