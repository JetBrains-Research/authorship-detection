package com.lz.game.rpc.core;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
/**
 * User: Teaey
 * Date: 13-7-13
 */
public class OioRpcConnection extends BaseRpcConnection
{
    public OioRpcConnection(String host, int port, int timeout)
    {
        super(host, port, timeout);
    }
    private Socket             socket;
    private HeaderInputStream  inputStream;
    private HeaderOutputStream outputStream;
    private final class HeaderInputStream extends FilterInputStream
    {
        public HeaderInputStream(InputStream in)
        {
            super(in);
        }
        public byte[] recv() throws IOException
        {
            ByteBuffer header = ByteBuffer.allocate(4);
            read(header.array());
            int length = header.getInt();
            byte[] data = new byte[length];
            read(data);
            return data;
        }
    }
    private final class HeaderOutputStream extends FilterOutputStream
    {
        public HeaderOutputStream(OutputStream out)
        {
            super(out);
        }
        public void send(byte[] data) throws IOException
        {
            int length = data.length;
            ByteBuffer header = ByteBuffer.allocate(4);
            header.putInt(length);
            write(header.array());
            write(data);
            flush();
        }
    }
    @Override
    public void connect()
    {
        if (!isConnected())
        {
            try
            {
                socket = new Socket();
                //socket.setReuseAddress(false);
                socket.setKeepAlive(true);
                socket.setTcpNoDelay(true);
                socket.connect(new InetSocketAddress(host, port), timeout);
                socket.setSoTimeout(timeout);
                outputStream = new HeaderOutputStream(socket.getOutputStream());
                inputStream = new HeaderInputStream(socket.getInputStream());
            } catch (IOException ex)
            {
                throw new RpcException(ex);
            }
        }
    }
    @Override
    public boolean isConnected()
    {
        return socket != null && socket.isBound() && !socket.isClosed() && socket.isConnected() && !socket.isInputShutdown() && !socket.isOutputShutdown();
    }
    @Override
    public void disconnect()
    {
        try
        {
            if (inputStream != null)
                inputStream.close();
        } catch (Exception e)
        {
            //ignore
        }
        try
        {
            if (outputStream != null)
                outputStream.close();
        } catch (Exception e)
        {
            //ignore
        }
        try
        {
            if (outputStream != null && !socket.isClosed())
            {
                socket.close();
            }
        } catch (Exception e)
        {
            //ignore
        }
    }
    @Override
    public void send(Rpc.RpcPacket packet)
    {
        connect();
        byte[] data = packet.toByteArray();
        try
        {
            outputStream.send(data);
        } catch (Exception e)
        {
            throw new RpcIOException(e);
        }
    }
    @Override
    public Rpc.RpcPacket sendWaitBack(Rpc.RpcPacket packet)
    {
        connect();
        byte[] reqData = packet.toByteArray();
        try
        {
            outputStream.send(reqData);
            byte[] respData = inputStream.recv();
            return Rpc.RpcPacket.parseFrom(respData);
        } catch (Exception e)
        {
            throw new RpcException(e);
        }
    }
    @Override
    public Rpc.RpcPacket read()
    {
        throw new UnsupportedOperationException("read");
    }
}
