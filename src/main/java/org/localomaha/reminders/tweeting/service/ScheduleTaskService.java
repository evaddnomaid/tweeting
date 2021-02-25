//package org.localomaha.reminders.tweeting.service;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.TimeZone;
//import java.util.concurrent.ScheduledFuture;
//
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.scheduling.TaskScheduler;
//import org.springframework.scheduling.support.CronTrigger;
//import org.springframework.stereotype.Service;
//
//// Start with https://thebackendguy.com/spring-schedule-tasks-or-cron-jobs-dynamically/
//
//@Service
//public class ScheduleTaskService {
//
//    // Task Scheduler
//    TaskScheduler scheduler;
//
//    // A map for keeping scheduled tasks
//    Map<Integer, ScheduledFuture<?>> jobsMap = new HashMap<>();
//
//    public ScheduleTaskService(TaskScheduler scheduler) {
//        this.scheduler = scheduler;
//    }
//
//    // Schedule Task to be executed every night at 00 or 12 am
//    public void addTaskToScheduler(int id, Runnable task) {
//        CronTrigger ct = new CronTrigger("0 0 0 * * ?", TimeZone.getTimeZone(TimeZone.getDefault().getID()));
//        System.out.println(ct);
//        ScheduledFuture<?> scheduledTask = scheduler.schedule(task, ct);
//        jobsMap.put(id, scheduledTask);
//    }
//
//    // Remove scheduled task
//    public void removeTaskFromScheduler(int id) {
//        ScheduledFuture<?> scheduledTask = jobsMap.get(id);
//        if (scheduledTask != null) {
//            scheduledTask.cancel(true);
//            jobsMap.put(id, null);
//        }
//    }
//
//    // A context refresh event listener
//    @EventListener({ContextRefreshedEvent.class})
//    void contextRefreshedEvent() {
//        // Get all tasks from DB and reschedule them in case of context restarted
//    }
//}
