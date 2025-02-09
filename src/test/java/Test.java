import com.voice.server.command.CommandConst;
import com.voice.server.command.GroupStrategy;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class Test {
    public static void main(String[] args) {
        Class unsafeClass = null;
        try {
            unsafeClass = Class.forName("sun.misc.Unsafe");
            Field field = unsafeClass.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            Unsafe unsafe = (Unsafe) field.get(null);

            Module baseModule = CommandConst.class.getModule();
            Class currentClass =  CommandConst.class.getClass();
            long addr = unsafe.objectFieldOffset(Class.class.getDeclaredField("module"));
            unsafe.getAndSetObject(currentClass, addr, baseModule);

            Field[] fields = GroupStrategy.class.getFields();
            for (Field field1 : fields) {
                field1.setAccessible(true);
                if(field1.getType().equals(String.class)){
                    System.out.println(field1.getName());
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }


    }

}
