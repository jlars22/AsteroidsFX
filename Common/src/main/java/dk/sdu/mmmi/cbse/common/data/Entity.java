package dk.sdu.mmmi.cbse.common.data;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.UUID;

public class Entity implements Serializable {

	private final UUID ID = UUID.randomUUID();

	private double[] polygonCoordinates;
	private double width;
	private double height;
	private double x;
	private double y;
	private double dx;
	private double dy;
	private double rotation;
	private int health;
	private LocalTime respawnTime;

	public String getID() {
		return ID.toString();
	}

	public void setPolygonCoordinates(double... coordinates) {
		this.polygonCoordinates = coordinates;
	}

	public double[] getPolygonCoordinates() {
		return polygonCoordinates;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getX() {
		return x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getY() {
		return y;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	public double getRotation() {
		return rotation;
	}

	public double getDX() {
		return dx;
	}

	public void setDX(double dx) {
		this.dx = dx;
	}

	public double getDY() {
		return dy;
	}

	public void setDY(double dy) {
		this.dy = dy;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public LocalTime getRespawnTime() {
		return respawnTime;
	}

	public void setRespawnTime(LocalTime respawnTime) {
		this.respawnTime = respawnTime;
	}

}
