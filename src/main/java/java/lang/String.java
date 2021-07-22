package java.lang;

/**
 * @Author tangmf
 * @Date 2021/7/20 9:11 下午
 * @Description
 */
public class String {
    public String toString(){
        return "hello";
    }

    public static void main(String[] args) {
        String s = new String();
        //根加载器中 BOOT中找到了String 类，抛出异常
        System.out.println(s.getClass().getClassLoader());
        System.out.println(s.toString());
    }
}
