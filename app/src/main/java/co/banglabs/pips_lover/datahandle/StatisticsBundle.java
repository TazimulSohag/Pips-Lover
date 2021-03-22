package co.banglabs.pips_lover.datahandle;

public class StatisticsBundle {

    int history_sl, history_total, history_tp1, history_tp2, history_untouched, recent_sl, recent_total, recent_tp1, recent_tp2, recent_untouched;

    public StatisticsBundle(int history_sl, int history_total, int history_tp1, int history_tp2, int history_untouched, int recent_sl, int recent_total, int recent_tp1, int recent_tp2, int recent_untouched) {
        this.history_sl = history_sl;
        this.history_total = history_total;
        this.history_tp1 = history_tp1;
        this.history_tp2 = history_tp2;
        this.history_untouched = history_untouched;
        this.recent_sl = recent_sl;
        this.recent_total = recent_total;
        this.recent_tp1 = recent_tp1;
        this.recent_tp2 = recent_tp2;
        this.recent_untouched = recent_untouched;
    }

    public StatisticsBundle(){

    }

    public int getHistory_sl() {
        return history_sl;
    }

    public int getHistory_total() {
        return history_total;
    }

    public int getHistory_tp1() {
        return history_tp1;
    }

    public int getHistory_tp2() {
        return history_tp2;
    }

    public int getHistory_untouched() {
        return history_untouched;
    }

    public int getRecent_sl() {
        return recent_sl;
    }

    public int getRecent_total() {
        return recent_total;
    }

    public int getRecent_tp1() {
        return recent_tp1;
    }

    public int getRecent_tp2() {
        return recent_tp2;
    }

    public int getRecent_untouched() {
        return recent_untouched;
    }
}
