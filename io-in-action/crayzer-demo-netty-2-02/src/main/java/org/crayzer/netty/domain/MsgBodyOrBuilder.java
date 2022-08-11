package org.crayzer.netty.domain;

/**
 * @author <a href="mailto:yeqi@banniuyun.com">夜骐</a>
 * @since 1.0.0
 */
public interface MsgBodyOrBuilder extends
    // @@protoc_insertion_point(interface_extends:org.crayzer.netty.domain.MsgBody)
    com.google.protobuf.MessageOrBuilder {

        /**
         * <code>string channelId = 1;</code>
         */
        java.lang.String getChannelId();
        /**
         * <code>string channelId = 1;</code>
         */
        com.google.protobuf.ByteString
        getChannelIdBytes();

        /**
         * <code>string msgInfo = 2;</code>
         */
        java.lang.String getMsgInfo();
        /**
         * <code>string msgInfo = 2;</code>
         */
        com.google.protobuf.ByteString
        getMsgInfoBytes();
}

