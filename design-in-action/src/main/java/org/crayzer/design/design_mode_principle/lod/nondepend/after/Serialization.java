package org.crayzer.design.design_mode_principle.lod.nondepend.after;

public class Serialization implements Serializable, Deserializable {
    @Override
    public String serialize(Object object) {
        String serializedResult = "...";
        // ...
        return serializedResult;
    }

    @Override
    public Object deserialize(String str) {
        Object deserializedResult = "...";
        // ...
        return deserializedResult;
    }
}
