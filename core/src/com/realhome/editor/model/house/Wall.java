
package com.realhome.editor.model.house;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.GdxRuntimeException;


public class Wall extends BaseModel {
	private static final int DEFAULT_WIDTH = 50;

	private int type;
	private float height;
	private int width = DEFAULT_WIDTH;
	private Floor floor;
	private final Point[] points = new Point[2];

	public Wall () {
		for (int i = 0; i < points.length; i++) {
			points[i] = new Point();
		}
	}

	public Wall(Wall wall, int id) {
		this();
		this.id = id;
		this.sync(wall);
	}

	public Wall cpy() {
		return new Wall(this, this.id);
	}

	public Vector2 dir(Vector2 out) {
		return dir(out, true);
	}

	public Vector2 dir(Vector2 out, boolean normalized) {
		return getPoint1().dir(getPoint0(), out, normalized);
	}

	public Wall sync(Wall target) {
		if(target.id != this.id) throw new GdxRuntimeException("Walls have not the same id");
		return set(target);
	}

	public Wall set(Wall target) {
		type = target.type;
		height = target.height;
		width = target.width;
		floor = target.floor;
		points[0].set(target.points[0]);
		points[1].set(target.points[1]);

		return this;
	}

	public int getType () {
		return type;
	}

	public Wall setType (int type) {
		this.type = type;
		return this;
	}
	
	public Floor getFloor() {
		return this.floor;
	}
	
	public Wall setFloor(Floor floor) {
		this.floor = floor;
		return this;
	}

	public Wall setWidth (int width) {
		this.width = width;
		return this;
	}

	public int getWidth () {
		return width;
	}

	public Point[] getPoints () {
		return points;
	}

	public Point getPoint0 () {
		return points[0];
	}

	public Wall setPoint0 (Point point0) {
		this.points[0].set(point0);
		return this;
	}

	public Wall setPoint0 (int x, int y) {
		this.points[0].set(x, y);
		return this;
	}

	public Point getPoint1 () {
		return points[1];
	}

	public Wall setPoint1 (Point point1) {
		this.points[1].set(point1);
		return this;
	}

	public Wall setPoint1 (int x, int y) {
		this.points[1].set(x, y);
		return this;
	}

	public float getHeight () {
		return height;
	}

	public Wall setHeight (float height) {
		this.height = height;
		return this;
	}

	public boolean isLinked (Wall other) {
		if (other.points[0].equals(points[0]) || other.points[0].equals(points[1]) || other.points[1].equals(points[0])
			|| other.points[1].equals(points[1])) return true;
		return false;
	}

	public Point getLinkedPoint (Wall other) {
		if (other.points[0].equals(points[0]) || other.points[0].equals(points[1]))
			return  other.points[0];
		if (other.points[1].equals(points[0]) || other.points[1].equals(points[1]))
			return other.points[1];
		return null;
	}

	public boolean equals(Wall other) {
		if( other == this ) return true;
		if( other.points[0].equals(points[0]) &&
			other.points[1].equals(points[1]) &&
			other.type == type &&
			other.height == height &&
			other.width == width)
			return true;
		return false;
	}
}
