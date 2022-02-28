package Test1;

public class IllegalTriangleException extends Exception{
    IllegalTriangleException (double a,double b,double c){
        // 传递给异常对象的信息
        super("Invalid sides: "+a+"  "+b+"  "+c);
    }
}
class Triangle{
    static boolean isTriangle (double a,double b,double c){
        if(a+b>c && a+c>b && b+c>a){
            System.out.println("isTriangle");
            return true;
        }else {
            try {
                // 不是Triangle，抛出异常对象
                throw new IllegalTriangleException(a,b,c);
            } catch (IllegalTriangleException e) {
                // 捕获异常对象，并获取该异常对象的信息
                System.out.println(e.getMessage());
            }
            return false;
        }

    }

    public static void main(String[] args) {
        Triangle.isTriangle(1,2,4);
    }
}