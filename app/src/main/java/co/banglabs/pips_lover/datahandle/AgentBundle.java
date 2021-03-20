package co.banglabs.pips_lover.datahandle;

public class AgentBundle {

    String agent_name;
    String agent_token;
    String new_users;
    String total_users;

    public AgentBundle(){


    }

    public AgentBundle(String agent_name, String agent_token, String new_users, String total_users) {
        this.agent_name = agent_name;
        this.agent_token = agent_token;
        this.new_users = new_users;
        this.total_users = total_users;
    }

    public String getAgent_name() {
        return agent_name;
    }

    public String getAgent_token() {
        return agent_token;
    }

    public String getNew_users() {
        return new_users;
    }

    public String getTotal_users() {
        return total_users;
    }
}
