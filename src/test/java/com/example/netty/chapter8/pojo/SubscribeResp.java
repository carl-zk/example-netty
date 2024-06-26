// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: SubscribeResp.proto
// Protobuf Java Version: 4.27.0

package com.example.netty.chapter8.pojo;

/**
 * Protobuf type {@code chapter8.SubscribeResp}
 */
public final class SubscribeResp extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:chapter8.SubscribeResp)
    SubscribeRespOrBuilder {
private static final long serialVersionUID = 0L;
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 27,
      /* patch= */ 0,
      /* suffix= */ "",
      SubscribeResp.class.getName());
  }
  // Use SubscribeResp.newBuilder() to construct.
  private SubscribeResp(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private SubscribeResp() {
    desc_ = "";
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.example.netty.chapter8.pojo.SubscribeRespProtos.internal_static_chapter8_SubscribeResp_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.example.netty.chapter8.pojo.SubscribeRespProtos.internal_static_chapter8_SubscribeResp_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.example.netty.chapter8.pojo.SubscribeResp.class, com.example.netty.chapter8.pojo.SubscribeResp.Builder.class);
  }

  public static final int SUBREQID_FIELD_NUMBER = 1;
  private int subReqId_ = 0;
  /**
   * <code>int32 subReqId = 1;</code>
   * @return The subReqId.
   */
  @java.lang.Override
  public int getSubReqId() {
    return subReqId_;
  }

  public static final int RESPCODE_FIELD_NUMBER = 2;
  private int respCode_ = 0;
  /**
   * <code>int32 respCode = 2;</code>
   * @return The respCode.
   */
  @java.lang.Override
  public int getRespCode() {
    return respCode_;
  }

  public static final int DESC_FIELD_NUMBER = 3;
  @SuppressWarnings("serial")
  private volatile java.lang.Object desc_ = "";
  /**
   * <code>string desc = 3;</code>
   * @return The desc.
   */
  @java.lang.Override
  public java.lang.String getDesc() {
    java.lang.Object ref = desc_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      desc_ = s;
      return s;
    }
  }
  /**
   * <code>string desc = 3;</code>
   * @return The bytes for desc.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getDescBytes() {
    java.lang.Object ref = desc_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      desc_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (subReqId_ != 0) {
      output.writeInt32(1, subReqId_);
    }
    if (respCode_ != 0) {
      output.writeInt32(2, respCode_);
    }
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(desc_)) {
      com.google.protobuf.GeneratedMessage.writeString(output, 3, desc_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (subReqId_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(1, subReqId_);
    }
    if (respCode_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(2, respCode_);
    }
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(desc_)) {
      size += com.google.protobuf.GeneratedMessage.computeStringSize(3, desc_);
    }
    size += getUnknownFields().getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.example.netty.chapter8.pojo.SubscribeResp)) {
      return super.equals(obj);
    }
    com.example.netty.chapter8.pojo.SubscribeResp other = (com.example.netty.chapter8.pojo.SubscribeResp) obj;

    if (getSubReqId()
        != other.getSubReqId()) return false;
    if (getRespCode()
        != other.getRespCode()) return false;
    if (!getDesc()
        .equals(other.getDesc())) return false;
    if (!getUnknownFields().equals(other.getUnknownFields())) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + SUBREQID_FIELD_NUMBER;
    hash = (53 * hash) + getSubReqId();
    hash = (37 * hash) + RESPCODE_FIELD_NUMBER;
    hash = (53 * hash) + getRespCode();
    hash = (37 * hash) + DESC_FIELD_NUMBER;
    hash = (53 * hash) + getDesc().hashCode();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.example.netty.chapter8.pojo.SubscribeResp parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.example.netty.chapter8.pojo.SubscribeResp parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.example.netty.chapter8.pojo.SubscribeResp parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.example.netty.chapter8.pojo.SubscribeResp parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.example.netty.chapter8.pojo.SubscribeResp parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.example.netty.chapter8.pojo.SubscribeResp parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.example.netty.chapter8.pojo.SubscribeResp parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static com.example.netty.chapter8.pojo.SubscribeResp parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static com.example.netty.chapter8.pojo.SubscribeResp parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static com.example.netty.chapter8.pojo.SubscribeResp parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.example.netty.chapter8.pojo.SubscribeResp parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static com.example.netty.chapter8.pojo.SubscribeResp parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.example.netty.chapter8.pojo.SubscribeResp prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessage.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code chapter8.SubscribeResp}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:chapter8.SubscribeResp)
      com.example.netty.chapter8.pojo.SubscribeRespOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.example.netty.chapter8.pojo.SubscribeRespProtos.internal_static_chapter8_SubscribeResp_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.example.netty.chapter8.pojo.SubscribeRespProtos.internal_static_chapter8_SubscribeResp_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.example.netty.chapter8.pojo.SubscribeResp.class, com.example.netty.chapter8.pojo.SubscribeResp.Builder.class);
    }

    // Construct using com.example.netty.chapter8.pojo.SubscribeResp.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      subReqId_ = 0;
      respCode_ = 0;
      desc_ = "";
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.example.netty.chapter8.pojo.SubscribeRespProtos.internal_static_chapter8_SubscribeResp_descriptor;
    }

    @java.lang.Override
    public com.example.netty.chapter8.pojo.SubscribeResp getDefaultInstanceForType() {
      return com.example.netty.chapter8.pojo.SubscribeResp.getDefaultInstance();
    }

    @java.lang.Override
    public com.example.netty.chapter8.pojo.SubscribeResp build() {
      com.example.netty.chapter8.pojo.SubscribeResp result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.example.netty.chapter8.pojo.SubscribeResp buildPartial() {
      com.example.netty.chapter8.pojo.SubscribeResp result = new com.example.netty.chapter8.pojo.SubscribeResp(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(com.example.netty.chapter8.pojo.SubscribeResp result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.subReqId_ = subReqId_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.respCode_ = respCode_;
      }
      if (((from_bitField0_ & 0x00000004) != 0)) {
        result.desc_ = desc_;
      }
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.example.netty.chapter8.pojo.SubscribeResp) {
        return mergeFrom((com.example.netty.chapter8.pojo.SubscribeResp)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.example.netty.chapter8.pojo.SubscribeResp other) {
      if (other == com.example.netty.chapter8.pojo.SubscribeResp.getDefaultInstance()) return this;
      if (other.getSubReqId() != 0) {
        setSubReqId(other.getSubReqId());
      }
      if (other.getRespCode() != 0) {
        setRespCode(other.getRespCode());
      }
      if (!other.getDesc().isEmpty()) {
        desc_ = other.desc_;
        bitField0_ |= 0x00000004;
        onChanged();
      }
      this.mergeUnknownFields(other.getUnknownFields());
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 8: {
              subReqId_ = input.readInt32();
              bitField0_ |= 0x00000001;
              break;
            } // case 8
            case 16: {
              respCode_ = input.readInt32();
              bitField0_ |= 0x00000002;
              break;
            } // case 16
            case 26: {
              desc_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000004;
              break;
            } // case 26
            default: {
              if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                done = true; // was an endgroup tag
              }
              break;
            } // default:
          } // switch (tag)
        } // while (!done)
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.unwrapIOException();
      } finally {
        onChanged();
      } // finally
      return this;
    }
    private int bitField0_;

    private int subReqId_ ;
    /**
     * <code>int32 subReqId = 1;</code>
     * @return The subReqId.
     */
    @java.lang.Override
    public int getSubReqId() {
      return subReqId_;
    }
    /**
     * <code>int32 subReqId = 1;</code>
     * @param value The subReqId to set.
     * @return This builder for chaining.
     */
    public Builder setSubReqId(int value) {

      subReqId_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>int32 subReqId = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearSubReqId() {
      bitField0_ = (bitField0_ & ~0x00000001);
      subReqId_ = 0;
      onChanged();
      return this;
    }

    private int respCode_ ;
    /**
     * <code>int32 respCode = 2;</code>
     * @return The respCode.
     */
    @java.lang.Override
    public int getRespCode() {
      return respCode_;
    }
    /**
     * <code>int32 respCode = 2;</code>
     * @param value The respCode to set.
     * @return This builder for chaining.
     */
    public Builder setRespCode(int value) {

      respCode_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <code>int32 respCode = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearRespCode() {
      bitField0_ = (bitField0_ & ~0x00000002);
      respCode_ = 0;
      onChanged();
      return this;
    }

    private java.lang.Object desc_ = "";
    /**
     * <code>string desc = 3;</code>
     * @return The desc.
     */
    public java.lang.String getDesc() {
      java.lang.Object ref = desc_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        desc_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string desc = 3;</code>
     * @return The bytes for desc.
     */
    public com.google.protobuf.ByteString
        getDescBytes() {
      java.lang.Object ref = desc_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        desc_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string desc = 3;</code>
     * @param value The desc to set.
     * @return This builder for chaining.
     */
    public Builder setDesc(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      desc_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <code>string desc = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearDesc() {
      desc_ = getDefaultInstance().getDesc();
      bitField0_ = (bitField0_ & ~0x00000004);
      onChanged();
      return this;
    }
    /**
     * <code>string desc = 3;</code>
     * @param value The bytes for desc to set.
     * @return This builder for chaining.
     */
    public Builder setDescBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      desc_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:chapter8.SubscribeResp)
  }

  // @@protoc_insertion_point(class_scope:chapter8.SubscribeResp)
  private static final com.example.netty.chapter8.pojo.SubscribeResp DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.example.netty.chapter8.pojo.SubscribeResp();
  }

  public static com.example.netty.chapter8.pojo.SubscribeResp getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<SubscribeResp>
      PARSER = new com.google.protobuf.AbstractParser<SubscribeResp>() {
    @java.lang.Override
    public SubscribeResp parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      Builder builder = newBuilder();
      try {
        builder.mergeFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(builder.buildPartial());
      } catch (com.google.protobuf.UninitializedMessageException e) {
        throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(e)
            .setUnfinishedMessage(builder.buildPartial());
      }
      return builder.buildPartial();
    }
  };

  public static com.google.protobuf.Parser<SubscribeResp> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<SubscribeResp> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.example.netty.chapter8.pojo.SubscribeResp getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

