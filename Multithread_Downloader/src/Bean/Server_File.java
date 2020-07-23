package Bean;

public class Server_File {
	private String name ;
	private String size;
	private  String bytes;
	
	
	public Server_File(String name, String size,String bytes ) {
		super();
		this.name = name;
		this.bytes = bytes;
		this.size = size;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getBytes() {
		return bytes;
	}
	public void setBytes(String bytes) {
		this.bytes = bytes;
	}
	

}
