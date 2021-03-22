package co.banglabs.pips_lover.datahandle;

import android.util.Log;

public class PairBundle {

    String pair_name;
    String pair_statas;
    String pair_action;
    String open_price;
    String stop_loss;
    String take_profit_1;
    String take_profit_2;
    String take_profit_3;
    String trade_result;
    String update_time;

    public PairBundle(){

        Log.d("outmsg", "null valued");

    }

    public PairBundle(String pair_name, String pair_statas, String pair_action, String open_price, String stop_loss, String take_profit_1, String take_profit_2, String trade_result, String LastUpdateDate) {

        this.pair_name = pair_name;
        this.pair_statas = pair_statas;
        this.pair_action = pair_action;
        this.open_price = open_price;
        this.stop_loss = stop_loss;
        this.take_profit_1 = take_profit_1;
        this.take_profit_2 = take_profit_2;
        this.take_profit_3 = "";
        this.trade_result = trade_result;
        this.update_time = LastUpdateDate;
        Log.d("outmsg", "multi valued");
    }

    public PairBundle(String pair_name) {
        this.pair_name = pair_name;
        this.pair_statas ="";
        this.pair_action ="";
        this.open_price ="";
        this.stop_loss ="";
        this.take_profit_1 ="";
        this.take_profit_2 ="";
        this.take_profit_3 ="";
        this.trade_result ="";
        this.update_time ="";
        Log.d("outmsg", "single valued");
    }


    public String getPair_name() {
        return pair_name;
    }

    public String getPair_statas() {
        return pair_statas;
    }

    public String getPair_action() {
        return pair_action;
    }

    public String getOpen_price() {
        return open_price;
    }

    public String getStop_loss() {
        return stop_loss;
    }

    public String getTake_profit_1() {
        return take_profit_1;
    }

    public String getTake_profit_2() {
        return take_profit_2;
    }

    public String getTrade_result() {
        return trade_result;
    }

    public String getUpdate_time() {
        return update_time;
    }
}
