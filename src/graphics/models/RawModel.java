package graphics.models;

public class RawModel {

	private int vaoId;
	private int vertexCount;
	private int indexCount;
	
	public RawModel(int id, int vertexCount, int indexCount) {
		this.vaoId = id;
		this.vertexCount = vertexCount;
		this.indexCount = indexCount;
	}
	
	public int getVAOId() {
		return this.vaoId;
	}
	
	public int getVertexCount() {
		return this.vertexCount;
	}

	public int getIndexCount() {
		return this.indexCount;
	}
	
}
