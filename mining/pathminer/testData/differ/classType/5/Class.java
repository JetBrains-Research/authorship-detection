class OuterClass {

    private int outerField;

    void methodWithLocalClass(final int finalParameter) {
        int notFinalVar = 0;
        notFinalVar++;

        class InnerLocalClass {
            void foo() {
                int b = OuterClass.this.outerField;
            }
        }
    }
}