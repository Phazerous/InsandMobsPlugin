package pro.diamondworld.insanemobs.common;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;

public class CountdownTimer implements Runnable {
    private final JavaPlugin plugin;
    private Integer assignedTaskId;

    private int timeLeft;
    private Runnable afterTimer;
    private Consumer<CountdownTimer> everySecond;

    public CountdownTimer(JavaPlugin plugin, int timeLeft, Runnable afterTimer, Consumer<CountdownTimer> everySecond) {
        this.plugin = plugin;
        this.afterTimer = afterTimer;
        this.timeLeft = timeLeft;
        this.everySecond = everySecond;
    }


    @Override
    public void run() {
        if (timeLeft < 1) {
            afterTimer.run();

            if (assignedTaskId != null) Bukkit.getScheduler().cancelTask(assignedTaskId);
            return;
        }

        everySecond.accept(this);

        timeLeft--;
    }

    public void scheduleTimer() {
        assignedTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this, 0L, 20L);
    }

    public int getMinutesLeft() {
        return timeLeft / 60;
    }

    public int getSecondsLeft() {
        int seconds = timeLeft;

        while (seconds > 60) {
            seconds -= 60;
        }

        return seconds;
    }
}
