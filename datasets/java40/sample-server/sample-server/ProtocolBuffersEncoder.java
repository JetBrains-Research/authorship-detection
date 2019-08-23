package com.lz.game.rpc.core;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.MessageLite;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import java.io.IOException;
import java.nio.ByteBuffer;
/**
 * User: Teaey
 * Date: 13-5-16
 */
public class ProtocolBuffersEncoder extends OneToOneEncoder
{
    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception
    {
        byte[] body = toByteArray(msg);
        if (null != body)
        {
            int length = body.length;
            int headerLength = CodedOutputStream.computeRawVarint32Size(length);
            byte[] header = new byte[headerLength];
            CodedOutputStream codedOutputStream = CodedOutputStream.newInstance(header);
            codedOutputStream.writeRawVarint32(length);
            codedOutputStream.flush();
            return ChannelBuffers.wrappedBuffer(header, body);
        }
        return msg;
    }
    public static byte[] toByteArray(Object msg)
    {
        byte[] body = null;
        if (msg instanceof MessageLite)
        {
            body = ((MessageLite) msg).toByteArray();
        }
        else if (msg instanceof MessageLite.Builder)
        {
            body = ((MessageLite.Builder) msg).build().toByteArray();
        }
        return body;
    }
    public static byte[] encode(Object msg) throws IOException
    {
        byte[] body = toByteArray(msg);
        if (null == body)
            return null;
        int length = body.length;
        int headerLength = CodedOutputStream.computeRawVarint32Size(length);
        ByteBuffer bb = ByteBuffer.allocate(headerLength + body.length);
        byte[] header = new byte[headerLength];
        CodedOutputStream codedOutputStream = CodedOutputStream.newInstance(header);
        codedOutputStream.writeRawVarint32(length);
        codedOutputStream.flush();
        bb.put(header);
        bb.put(body);
        return bb.array();
    }
}
