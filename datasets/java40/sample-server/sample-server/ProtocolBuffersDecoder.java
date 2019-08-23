package com.lz.game.rpc.core;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.MessageLite;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBufferInputStream;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.CorruptedFrameException;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
/**
 * User: Teaey
 * Date: 13-5-16
 */
public class ProtocolBuffersDecoder extends FrameDecoder
{
    private final MessageLite prototype;
    private final ExtensionRegistry extensionRegistry;

    /**
     * Creates a new instance.
     */
    public ProtocolBuffersDecoder(MessageLite prototype)
    {
        this(prototype, null);
    }

    public ProtocolBuffersDecoder(MessageLite prototype,
            ExtensionRegistry extensionRegistry)
    {
        if (prototype == null)
        {
            throw new NullPointerException("prototype");
        }
        this.prototype = prototype.getDefaultInstanceForType();
        this.extensionRegistry = extensionRegistry;
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel,
            ChannelBuffer buffer) throws Exception
    {
        buffer.markReaderIndex();
        ChannelBuffer body = null;
        final byte[] buf = new byte[5];
        for (int i = 0; i < buf.length; i++)
        {
            if (!buffer.readable())
            {
                buffer.resetReaderIndex();
                return null;
            }

            buf[i] = buffer.readByte();
            if (buf[i] >= 0)
            {
                int length = CodedInputStream.newInstance(buf, 0, i + 1)
                        .readRawVarint32();
                if (length < 0)
                {
                    channel.close();//close the channel if a error happend
                    throw new CorruptedFrameException("negative length: "
                            + length + " close channel " + channel);
                }

                if (buffer.readableBytes() < length)
                {
                    buffer.resetReaderIndex();
                    return null;
                } else
                {
                    body = buffer.readBytes(length);
                    break;
                }
            }
        }
        if (null == body)
        {
            // Couldn't find the byte whose MSB is off.
            channel.close();//close the channel if a error happend
            throw new CorruptedFrameException("length wider than 32-bit"
                    + " close channel " + channel);
        } else
        {
            if (body.hasArray())
            {
                final int offset = body.readerIndex();
                if (extensionRegistry == null)
                {
                    return prototype
                            .newBuilderForType()
                            .mergeFrom(body.array(),
                                    body.arrayOffset() + offset,
                                    body.readableBytes()).build();
                } else
                {
                    return prototype
                            .newBuilderForType()
                            .mergeFrom(body.array(),
                                    body.arrayOffset() + offset,
                                    body.readableBytes(), extensionRegistry)
                            .build();
                }
            } else
            {
                if (extensionRegistry == null)
                {
                    return prototype.newBuilderForType()
                            .mergeFrom(new ChannelBufferInputStream(body))
                            .build();
                } else
                {
                    return prototype
                            .newBuilderForType()
                            .mergeFrom(new ChannelBufferInputStream(body),
                                    extensionRegistry).build();
                }
            }
        }
    }
}
