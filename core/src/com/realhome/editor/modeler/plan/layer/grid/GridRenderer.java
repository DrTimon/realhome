package com.realhome.editor.modeler.plan.layer.grid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class GridRenderer {
	private final int width;
	private final int height;
	private final int tileSize;
	private Mesh mesh;
	static private final float GREY = 0.5f;
	private final Color color = new Color(GREY, GREY, GREY, GREY);


	// Shader
	private ShaderProgram shader;
	private static final String vertexShader = "com/realhome/editor/modeler/plan/layer/grid/grid_vertex.glsl";
	private static final String fragmentShader = "com/realhome/editor/modeler/plan/layer/grid/grid_fragment.glsl";

	// Used for mesh creation
	private int vertexIdx;
	private int vertexSize;
	private float[] vertices;

	// Used for computation
	private final Vector2 point0 = new Vector2();
	private final Vector2 point1 = new Vector2();

	public GridRenderer(int width, int height, int tileSize) {
		this.width = width;
		this.height = height;
		this.tileSize = tileSize;

		initShader();
		initMesh();
	}

	private void initShader() {
		String vertex = Gdx.files.classpath(vertexShader).readString();
		String fragment = Gdx.files.classpath(fragmentShader).readString();
		shader = new ShaderProgram(vertex, fragment);
		if (!shader.isCompiled()) throw new GdxRuntimeException(shader.getLog());
	}

	private void initMesh() {
		// Compute informations
		int nbLinesWidth = width / tileSize;
		int nbLinesHeight = height / tileSize;
		int nbLines = nbLinesWidth + nbLinesHeight;
		int maxVertices = nbLines * 2 + 4; // 2 points per line

		// Create mesh
		VertexAttributes attributes = new VertexAttributes(
			new VertexAttribute(Usage.Position, 2, ShaderProgram.POSITION_ATTRIBUTE));
		mesh = new Mesh(true, maxVertices, 0, attributes);

		// Compute vertices
		vertices = new float[maxVertices * (mesh.getVertexAttributes().vertexSize / 4)];
		vertexSize = mesh.getVertexAttributes().vertexSize / 4;

		// Width lines
		int xMin = -width/2, xMax = width/2;
		for(int x = xMin; x <= xMax; x += tileSize) {
			heightLine(x);
		}

		// Height lines
		int yMin = -height/2, yMax = height/2;
		for(int y = yMin; y <= yMax; y += tileSize) {
			widthLine(y);
		}

		// Finalize mesh
		mesh.setVertices(vertices);
	}

	public void render(Matrix4 projViewTrans) {
		shader.begin();
		shader.setUniformMatrix("u_projViewTrans", projViewTrans);
		shader.setUniformf("u_color", color);
		mesh.render(shader, GL20.GL_LINES);
		shader.end();
	}

	private void heightLine(int x) {
		point0.set(x, -height/2);
		point1.set(x, height/2);
		line(point0, point1);
	}

	private void widthLine(int y) {
		point0.set(-width/2, y);
		point1.set(width/2, y);
		line(point0, point1);
	}

	private void line(Vector2 point0, Vector2 point1) {
		vertex(point0.x, point0.y);
		vertex(point1.x, point1.y);
	}

	private void vertex (float x, float y) {
		int idx = vertexIdx;
		vertices[idx] = x;
		vertices[idx + 1] = y;
		vertexIdx += vertexSize;
	}

	public void dispose () {
		shader.dispose();
		mesh.dispose();
	}
}