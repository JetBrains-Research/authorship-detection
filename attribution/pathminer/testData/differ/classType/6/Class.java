class OuterClass {
    void inOuterClass() {
        System.out.println("class");
    }
    class InnerClass {
        void inInnerClass() {
            System.out.println("foo");
        }
    }
    static class NestedClass {
        void inNestedClass() {

            class LocalClass {

                void inLocalClass() {
                    System.out.println("foo");

                    final Object object = new Object() {
                        void inAnonymousClass() {
                            System.out.println("foo");
                        }
                    };
                }
            }

            System.out.println("foo");
        }
    }

}
