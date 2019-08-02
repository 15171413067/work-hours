package cn.hnisi.worklog;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
	// write your code here
        String defaultFilePath = "加班工时记录.txt";
        String filePath;
        if(args == null || args.length == 0){
            filePath = defaultFilePath;
        }else{
            filePath = args[0];
        }

        WorkLogFactory workLogFactory = new WorkLogFactory(filePath);
        try{
            workLogFactory.build();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}

class WorkLogFactory{
    private final String sourceFile;
    private List<WorkLogBlock> workLogBlocks;
    private List<WorkLog> workLogs;

    public WorkLogFactory(String sourceFile){
        this.sourceFile = sourceFile;
    }

    public void build() throws Exception {

        // 读取文件
        File file = new File(sourceFile);
        if(!file.canRead()){
            throw new Exception("文件不存在："+file.getAbsolutePath());
        }

        BufferedReader br = new BufferedReader(new FileReader(file));
        this.workLogBlocks = new ArrayList<>();
        WorkLogBlock workLogBlock = null;
        for(String line = br.readLine();line != null;line = br.readLine()){
            //按行处理
            //如果首字母为2，则为内容栏，反之为标题栏
            if(!line.substring(0,1).equals("2")){
                workLogBlock = new WorkLogBlock(line);
                this.workLogBlocks.add(workLogBlock);
            }else {
                workLogBlock.addContentLine(line);
            }
        }
        br.close();

        //读取完毕,处理workLogBlocks
        this.workLogs = new ArrayList<>();
        for (WorkLogBlock block : this.workLogBlocks) {
            workLogs.addAll(WorkLogBuilder.build(block));
        }


        //输出
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(sourceFile.substring(0,sourceFile.lastIndexOf("."))+".csv")));
        for (WorkLog workLog : this.workLogs) {
            bw.write(workLog.output());
            bw.newLine();
        }
        bw.close();
    }
}

class WorkLogBlock{
    private String titleLine;
    private List<String> contentLines;

    public WorkLogBlock(String titleLine){
        this.titleLine = titleLine;
        this.contentLines = new ArrayList<>();
    }

    public String getTitleLine() {
        return titleLine;
    }

    public void addContentLine(String line){
        this.contentLines.add(line);
    }

    public List<String> getContentLines() {
        return contentLines;
    }
}

class WorkLog{
    private String name;
    private String qq;
    private String logTime;
    private String date;
    private String project;
    private String task;
    private String workHours;
    private String startTime;
    private String endTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getWorkHours() {
        return workHours;
    }

    public void setWorkHours(String workHours) {
        this.workHours = workHours;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String output(){
        String separator = ",";
        return name+separator+date+separator+project+separator+task+separator+workHours+separator+startTime+separator+endTime;
    }
}

class WorkLogBuilder{
    public static List<WorkLog> build(WorkLogBlock workLogBlock){
        try{
            List<WorkLog> workLogs = new ArrayList<>();

            //处理标题行
            String[] fields = workLogBlock.getTitleLine().split(" ");
            String name = fields[0].substring(0,fields[0].indexOf("("));
            String qq = fields[0].substring(fields[0].indexOf("(")+1,fields[0].length()-1);
            String logTime = fields[1];

            //处理内容行
            for(String line : workLogBlock.getContentLines()){
                fields = line.split("\\s+");
                WorkLog workLog = new WorkLog();
                workLog.setName(name);
                workLog.setQq(qq);
                workLog.setLogTime(logTime);
                workLog.setDate(fields[0]);
                workLog.setProject(fields[1]);
                workLog.setTask(fields[2]);
                workLog.setWorkHours(fields[3].toUpperCase().replace("H",""));
                workLog.setStartTime(fields[4].split("-")[0]);
                workLog.setEndTime(fields[4].split("-")[1]);
                workLogs.add(workLog);
            }
            return workLogs;
        }catch(Exception ex){
            throw new RuntimeException("格式不符合要求："+workLogBlock.getTitleLine(),ex);
        }

    }
}