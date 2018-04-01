package baseballgame;

import edu.princeton.cs.algs4.*;

import java.util.HashMap;
import java.util.Map;

public class BaseballElimination {

    private int teamNums;
    private Map<String, TeamInfo> teams;
    private int[][] games;
    private int nodesNum;
    private int flowFromS;
    private int matchesOfTeams;
    private String[] id2TeamName;
    private Map<Integer, Integer> v2id;

    public BaseballElimination(String filename){
        In in = new In(filename);
        this.teamNums = Integer.parseInt(in.readLine());
        teams = new HashMap<>();
        games = new int[teamNums][teamNums];
        id2TeamName = new String[teamNums];

        int id = 0;
        while(in.hasNextLine()){
            String line = in.readLine().trim();
            String[] tokens = line.split(" ");
            String name = tokens[0];
            int wins = Integer.parseInt(tokens[1]);
            int loss = Integer.parseInt(tokens[2]);
            int left = Integer.parseInt(tokens[3]);
            teams.put(name, new TeamInfo(id, wins, loss, left));
            id2TeamName[id] = name;
            for(int i = 0; i < this.teamNums; i++) {
                games[id][i] = Integer.parseInt(tokens[4+i]);
            }
            id++;
        }
    }

    public int numberOfTeams() {
        return this.teamNums;
    }

    /*
     * all teams
     */
    public Iterable<String> teams() {
        return this.teams.keySet();
    }

    /*
     * number of remaining games between team1 and team2
     */
    public int against(String team1, String team2) {
        isValid(team1);
        isValid(team2);
        int i = this.teams.get(team1).id;
        int j = this.teams.get(team2).id;
        return this.games[i][j];
    }

    /*
     * number of wins for given team
     */
    public int wins(String team) {
        isValid(team);
        return this.teams.get(team).wins;
    }

    /*
     * number of losses for given team
     */
    public int losses(String team) {
        isValid(team);
        return this.teams.get(team).loss;
    }

    /*
     * number of remaining games for given team
     */
    public int remaining(String team) {
        isValid(team);
        return this.teams.get(team).left;
    }

    private void isValid(String team) {
        if (!this.teams.containsKey(team))
            throw new  java.lang.IllegalArgumentException();
    }

    /*
     * is given team eliminated?
     * 如果输出的流大于FF算法得到的最大流，说明有队伍就算给定的team后面的比赛全赢也没办法超过
     */
    public boolean isEliminated(String team){
        isValid(team);
        FlowNetwork G = constructFlowNetwork(team);
        if (G == null) return true;
        FordFulkerson maxflow = new FordFulkerson(G, 0, nodesNum - 1);
        return this.flowFromS > maxflow.value();
    }

    private FlowNetwork constructFlowNetwork(String team){
        TeamInfo teamInfo = this.teams.get(team);
        this.matchesOfTeams = (this.teamNums - 1) * (this.teamNums - 2) / 2;
        this.nodesNum = matchesOfTeams + this.teamNums + 1;
        FlowNetwork G = new FlowNetwork(nodesNum);
        int id = teamInfo.id;
        int mostWins = teamInfo.wins + teamInfo.left;
        this.flowFromS = 0;
        int index = 1, source = 0, terminal = nodesNum - 1;
        for (int i = 0, matchsOfI = i; i < this.teamNums; i++, matchsOfI++){
            if (i == id){
                matchsOfI--;
                continue;
            }
            for (int j = i + 1, matchsOfJ = matchsOfI + 1; j < this.teamNums; j++, matchsOfJ++){
                if (j == id){
                    matchsOfJ--;
                    continue;
                }
                G.addEdge(new FlowEdge(source, index, games[i][j]));
                this.flowFromS = this.flowFromS + games[i][j];
                G.addEdge(new FlowEdge(index, matchsOfI+1+matchesOfTeams, Double.POSITIVE_INFINITY));
                G.addEdge(new FlowEdge(index, matchsOfJ+1+matchesOfTeams, Double.POSITIVE_INFINITY));
                v2id.put(matchsOfI+1+matchesOfTeams, i);
                v2id.put(matchsOfJ+1+matchesOfTeams, j);
                index++;
            }
            int againstTeamWins = this.teams.get(this.id2TeamName[i]).wins;
            if ((mostWins - againstTeamWins) < 0) return null;
            G.addEdge(new FlowEdge(matchsOfI+1+matchesOfTeams, terminal, mostWins - againstTeamWins));
        }
        return G;
    }

    /*
     * subset R of teams that eliminates given team; null if not eliminated
     */
    public Iterable<String> certificateOfElimination(String team){
        isValid(team);
        if(!isEliminated(team))
            return null;
        else{
            Queue<String> certificates = new Queue<String>();
            int idOfGivenTeam = this.teams.get(team).id;
            FlowNetwork G = constructFlowNetwork(team);
            if (G == null){
                int mostWins = teams.get(team).wins + teams.get(team).left;
                for (int i = 0; i < this.teamNums; i++){
                    if (i == idOfGivenTeam) continue;;
                    TeamInfo teamInfo = teams.get(this.id2TeamName[i]);
                    if(mostWins < teamInfo.wins)
                        certificates.enqueue(this.id2TeamName[i]);
                }
            } else {
                FordFulkerson maxflow = new FordFulkerson(G, 0, nodesNum-1);
                for(int v = 1+this.matchesOfTeams; v < this.teamNums + this.matchesOfTeams; v++) {
                    if(maxflow.inCut(v)) {
                        int id = this.v2id.get(v);
                        certificates.enqueue(this.id2TeamName[id]);
                    }
                }
            }
            return certificates;
        }
    }


    private class TeamInfo{
        int id;
        int wins;
        int loss;
        int left;
        public TeamInfo(int id, int wins, int loss, int left) {
            this.id = id;
            this.wins = wins;
            this.loss = loss;
            this.left = left;
        }
    }

}
