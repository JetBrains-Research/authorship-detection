class OuterClass {
    static int staticOuterField;

    static class StaticInnerClass {
        int getStaticOuterField() {
            return OuterClass.staticOuterField;
        }
    }

}