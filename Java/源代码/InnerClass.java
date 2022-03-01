package Test1;

public class InnerClass {

    public static void main(String[] args){
        // 匿名内部类可作为对象进行传递
        InnerClass.yly(new AI() {
            // 重写方法
            @Override
            public void smile() {
                System.out.println("yly微微一笑...");
            }
        });
    }
        // 静态方法，参数类型为接口类型(形参)
    public static void yly(AI ai){
        // 执行重写的smile()方法
        ai.smile();
    }

}

interface AI{
    void smile();
}