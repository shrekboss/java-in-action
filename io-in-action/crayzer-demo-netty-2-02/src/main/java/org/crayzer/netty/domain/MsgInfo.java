package org.crayzer.netty.domain;

/**
 * @author <a href="mailto:yeqi@banniuyun.com">夜骐</a>
 * @since 1.0.0
 */
public final class MsgInfo {
    private MsgInfo() {}
    public static void registerAllExtensions(
            com.google.protobuf.ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(
            com.google.protobuf.ExtensionRegistry registry) {
        registerAllExtensions(
                (com.google.protobuf.ExtensionRegistryLite) registry);
    }
    static final com.google.protobuf.Descriptors.Descriptor
            internal_static_org_crayzer_netty_domain_MsgBody_descriptor;
    static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
            internal_static_org_crayzer_netty_domain_MsgBody_fieldAccessorTable;

    public static com.google.protobuf.Descriptors.FileDescriptor
    getDescriptor() {
        return descriptor;
    }
    private static  com.google.protobuf.Descriptors.FileDescriptor
            descriptor;
    static {
        java.lang.String[] descriptorData = {
                "\n\rMsgInfo.proto\022\035org.crayzer.netty." +
                        "domain\"-\n\007MsgBody\022\021\n\tchannelId\030\001 \001(\t\022\017\n\007" +
                        "msgInfo\030\002 \001(\tB*\n\035org.crayzer.netty." +
                        "domainB\007MsgInfoP\001b\006proto3"
        };
        com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
                new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
                    @Override public com.google.protobuf.ExtensionRegistry assignDescriptors(
                            com.google.protobuf.Descriptors.FileDescriptor root) {
                        descriptor = root;
                        return null;
                    }
                };
        com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData,
                        new com.google.protobuf.Descriptors.FileDescriptor[] {
                        }, assigner);
        internal_static_org_crayzer_netty_domain_MsgBody_descriptor =
                getDescriptor().getMessageTypes().get(0);
        internal_static_org_crayzer_netty_domain_MsgBody_fieldAccessorTable = new
                com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
                internal_static_org_crayzer_netty_domain_MsgBody_descriptor,
                new java.lang.String[] { "ChannelId", "MsgInfo", });
    }

    // @@protoc_insertion_point(outer_class_scope)
}
