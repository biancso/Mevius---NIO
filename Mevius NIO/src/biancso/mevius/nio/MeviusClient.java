package biancso.mevius.nio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class MeviusClient {
	private final SocketChannel sc;

	public MeviusClient(InetSocketAddress addr) throws IOException {
		sc = SocketChannel.open(addr);
	}

	protected MeviusClient(SocketChannel sc) {
		this.sc = sc;
	}

	public void sendPacket(Object obj) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.flush();
		oos.writeObject(obj);
		oos.flush();
		sc.write(ByteBuffer.wrap(baos.toByteArray()));
	}
}
