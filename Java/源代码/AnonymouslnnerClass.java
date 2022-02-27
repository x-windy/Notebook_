package Test1;

public class AnonymouslnnerClass {

    public static void main(String[] args) {
        /*
           编译看左边，运行看右边
           编译类型：AI
           运行类型：匿名内部类 XXXX => Test$1 分配类名

           // 匿名内部类只能使用一次，就消失了
           // 匿名内部类
           class XXXX implements AI{
            @Override
            public void say() {
            }
           }
           右边new了一个实现接口的类，在jdk底层创建了一个匿名内部类 Test$1
           并创建了Test$1对象实例，并返回地址
         */
        AI yly = new AI() {
            @Override
            public void say() {
                System.out.println("我是天才。");
            }
        };
        yly.say();
        System.out.println("yly的类名：" + yly.getClass());
    }

}
interface AI{
    public void say();
}
