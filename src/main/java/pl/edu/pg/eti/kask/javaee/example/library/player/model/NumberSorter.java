package pl.edu.pg.eti.kask.javaee.example.library.player.model;

import java.util.Comparator;

public class NumberSorter implements Comparator<Player>
{
    @Override
    public int compare(Player player1, Player player2) {
        return player1.getNumber().compareTo(player2.getNumber());
    }
}
