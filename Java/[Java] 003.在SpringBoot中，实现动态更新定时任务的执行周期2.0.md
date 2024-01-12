# 在 SpringBoot 中，实现动态更新定时任务的执行周期 2.0

因为相同的原因，nacos 不能时时修改，所以需要把定时任务的配置移到数据库中。
定时任务改用 Quartz，比较方便管理，所以实现起来就比较简单，只要在修改配置后重新加载定时任务就行。

Quartz 任务管理工具类：

```java
@Component
public class QuartzManager {
    @Resource(name = "scheduler")
    Scheduler scheduler;

    /**
     * 添加任务
     *
     * @param task
     */
    @SuppressWarnings("unchecked")
    public void addJob(SysTaskSchedule task) {
        try {
            Class taskClass = Class.forName(task.getClassName());
            // 任务名称和组构成任务 key
            JobDetail jobDetail = JobBuilder
                    .newJob(taskClass)
                    .withIdentity(task.getTaskName(), task.getTaskName())
                    .build();
            jobDetail.getJobDataMap().put("task_schedule", task);

            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(task.getTaskName(), task.getTaskName())// 触发器 key
                    .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND))
                    .withSchedule(CronScheduleBuilder.cronSchedule(task.getCronExpression())).startNow().build();

            scheduler.scheduleJob(jobDetail, trigger);

            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 暂停任务
     *
     * @param task
     * @throws SchedulerException
     */
    @SuppressWarnings("unchecked")
    public void pauseJob(SysTaskSchedule task) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(task.getTaskName(), task.getTaskName());
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复任务
     *
     * @param task
     * @throws SchedulerException
     */
    @SuppressWarnings("unchecked")
    public void resumeJob(SysTaskSchedule task) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(task.getTaskName(), task.getTaskName());
        scheduler.resumeJob(jobKey);
    }

    /**
     * 删除任务
     *
     * @param task
     * @throws SchedulerException
     */
    @SuppressWarnings("unchecked")
    public void deleteJob(SysTaskSchedule task) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(task.getTaskName(), task.getTaskName());
        scheduler.deleteJob(jobKey);
    }

    /**
     * 立即执行任务
     *
     * @param task
     * @throws SchedulerException
     */
    @SuppressWarnings("unchecked")
    public void runJobNow(SysTaskSchedule task) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(task.getTaskName(), task.getTaskName());
        scheduler.triggerJob(jobKey);
    }

    /**
     * 更新任务的 cron 表达式
     *
     * @param task
     * @throws SchedulerException
     */
    @SuppressWarnings("unchecked")
    public void updateJobCron(SysTaskSchedule task) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(task.getTaskName(), task.getTaskName());
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(task.getCronExpression());
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
        scheduler.rescheduleJob(triggerKey, trigger);
    }
}
```

定时任务表实体类：

```java
@TableName("sys_task_schedule")
public class SysTaskSchedule implements Serializable {

    private static final long serialVersionUID = 850825836953644960L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 方法名
     */
    @ApiModelProperty(value = "方法名")
    private String className;

    /**
     * 任务名
     */
    @ApiModelProperty(value = "任务名")
    private String taskName;

    /**
     * cron 表达式
     */
    @ApiModelProperty(value = "cron 表达式")
    private String cronExpression;

    /**
     * 任务状态，0 暂停，1 启用
     */
    @ApiModelProperty(value = "任务状态，0 暂停，1 启用")
    private Integer taskStatus;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "SysTaskSchedule{" +
                "id=" + id +
                ", module=" + module +
                ", methodName=" + className +
                ", taskName=" + taskName +
                ", cronExpression=" + cronExpression +
                ", taskStatus=" + taskStatus +
                ", remark=" + remark +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                "}";
    }
}
```

初始化定时任务和刷新定时任务的代码：

```java
    public void initSchedule() {
        List<SysTaskSchedule> jobList = this.baseMapper.selectList(Wrappers.<SysTaskSchedule>lambdaQuery().eq(SysTaskSchedule::getTaskStatus, 1));
        for (SysTaskSchedule task : jobList) {
            quartzManager.addJob(task);
        }
    }

    public void reloadSchedule() {
        List<SysTaskSchedule> jobList = this.baseMapper.selectList(Wrappers.emptyWrapper());
        for (SysTaskSchedule task : jobList) {
            try {
                quartzManager.deleteJob(task);
            } catch (SchedulerException se) {
                se.printStackTrace();
            }
        }
        jobList = this.baseMapper.selectList(Wrappers.<SysTaskSchedule>lambdaQuery().eq(SysTaskSchedule::getTaskStatus, 1));
        for (SysTaskSchedule task : jobList) {
            quartzManager.addJob(task);
        }
    }
```

以上就是整体原因的简单分析及解决方案。
