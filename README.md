# work-hours
工时统计辅助工具，快速从QQ聊天记录中生成加班申请记录

## 使用说明
1. 从加班申请聊天群中复制聊天记录至txt文件中
2. 清理与加班申请无关的聊天记录，清理后结果如下

> 彭X(144XXX1208) 14:00:39
> 
> 2018/8/22 东莞 东莞社保医疗保险政策改革医养结合2018开发编码 1H
>
> 彭X(144XXX1208) 11:04:55
>
> 2018/8/23 东莞 东莞社保医疗保险政策改革医养结合2018开发编码 3.5H
> 
> 陈X忠(143XXXX3533) 11:05:39
> 
> 2018/8/23  江门  用户问题报告分析处理  2H
> 
> 林X辉(534XXXX08) 11:06:02
> 
> 2018/8/22 江门 用户问题报告分析处理 3H
> 
> 2018/8/23 江门 用户问题报告分析处理 3H

3. 修改文件路径，运行
```
public static void main(String[] args) {
	// write your code here
        WorkLogFactory workLogFactory = new WorkLogFactory("C:\\Users\\dupengfei\\Desktop\\加班工时记录.txt");
        try{
            workLogFactory.build();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
```

4. 相同路径下生成同名的.csv文件，复制进加班记录tab页即可
