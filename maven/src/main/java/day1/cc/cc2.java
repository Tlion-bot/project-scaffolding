package day1.cc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class Person implements Serializable {

    private static final long serialVersionUID = -5809452578272945389L;
    private String name;
    public Person(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    private void readObject(ObjectInputStream s)throws IOException, ClassNotFoundException{
        s.defaultReadObject();
        Runtime.getRuntime().exec("calc.exe");

    }
}


public class cc2 {
    public static void main(String[] args) throws Exception {
        /**序列化Person对象**/
        Serialize();
        /**反序列Perons对象**/
        Person p = UnSerialize();
        System.out.println("name="+p.getName());


    }

    /**
     * Description: 序列化Person对象
     */
    private static void Serialize() throws FileNotFoundException, IOException {
        Person person = new Person("nick");
        /** ObjectOutputStream 对象输出流，将Person对象存储到E盘的Person.txt文件中，完成对Person对象的序列化操作 **/
        ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(new File("D:\\Person.txt")));
        oo.writeObject(person);
        System.out.println("Person对象序列化成功！");
        oo.close();

    }

    /**
     * Description: 反序列Perons对象
     */
    private static Person UnSerialize() throws Exception, IOException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("D:\\Person.txt")));
        Person person = (Person) ois.readObject();
        System.out.println("Person对象反序列化成功！");
        return person;
    }


}
