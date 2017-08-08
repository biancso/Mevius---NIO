package biancso.mevius.nio;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class MeviusServer extends Thread {
	private final ServerSocketChannel ssc;
	private final Selector selector;

	public MeviusServer(int port) throws IOException {
		selector = Selector.open();
		ssc = ServerSocketChannel.open();
		ssc.configureBlocking(false);
		ssc.bind(new InetSocketAddress(port));
		ssc.register(selector, SelectionKey.OP_ACCEPT);
	}

	public void run() {
		while (true) {
			try {
				selector.select();
				Iterator<SelectionKey> it = selector.selectedKeys().iterator();
				while (it.hasNext()) {
					SelectionKey k = it.next();
					it.remove();

					if (k.isAcceptable()) {
						accept(k);
					} else if (k.isReadable()) {
						read(k);
					} else if (k.isWritable()) {

					}
					continue;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void accept(SelectionKey k) {
		try {
			ServerSocketChannel sc = (ServerSocketChannel) k.channel();
			SocketChannel channel = sc.accept();
			channel.configureBlocking(false);
			channel.register(selector, SelectionKey.OP_READ);
			System.out.println(channel.socket().getInetAddress().getHostAddress());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void read(SelectionKey k) {
		try {
			SocketChannel channel = (SocketChannel) k.channel();
			ByteBuffer data = ByteBuffer.allocate(1024);
			data.clear();
			channel.read(data);
			ByteArrayInputStream bais = new ByteArrayInputStream(data.array());
			ObjectInputStream ois = new ObjectInputStream(bais);
			People people = (People) ois.readObject();
			System.out.println(people.getName() + " Age : " + people.getAge());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
