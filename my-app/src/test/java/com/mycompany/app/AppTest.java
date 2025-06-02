package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.io.*;
import javax.swing.*;
import java.awt.Component;
import java.lang.reflect.Field;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;


class AppTests {

    @Test
    void playerInitialization() {
        Player player = new Player();
        assertFalse(player.selected);
        assertFalse(player.win);
        assertEquals(0, player.move);
        assertEquals('\u0000', player.symbol);
    }

    @Test
    void cellProperties() {
        TicTacToeCell cell = new TicTacToeCell(5, 1, 2);
        assertEquals(5, cell.getNum());
        assertEquals(1, cell.getCol());
        assertEquals(2, cell.getRow());
        assertEquals(' ', cell.getMarker());
    }

    @Test
    void setMarker() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
    }

    @Test
    void gameInitialization() {
        Game game = new Game();
        assertEquals(State.PLAYING, game.state);
        assertArrayEquals(new char[]{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, game.board);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
    }

    @Test
    void checkState_WinX() {
        Game game = new Game();
        game.symbol = 'X';
        char[] board = {'X','X','X',' ',' ',' ',' ',' ',' '};
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void checkState_Draw() {
        Game game = new Game();
        game.symbol = 'X';
        char[] board = {'X','O','X','X','O','O','O','X','X'};
        assertEquals(State.DRAW, game.checkState(board));
    }

    @Test
    void generateMoves() {
        Game game = new Game();
        ArrayList<Integer> moves = new ArrayList<>();
        game.board = new char[]{'X', ' ', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        game.generateMoves(game.board, moves);
        assertArrayEquals(new Object[]{1, 3, 4, 5, 6, 7, 8}, moves.toArray());
    }

    @Test
    void evaluatePosition_Win() {
        Game game = new Game();
        game.symbol = 'X';
        char[] board = {'X','X','X',' ',' ',' ',' ',' ',' '};
        assertEquals(Game.INF, game.evaluatePosition(board, game.player1));
    }

    @Test
    void miniMax_BlockWin() {
        Game game = new Game();
        game.board = new char[]{'X','X',' ',' ','O',' ',' ',' ',' '};
        game.player2.symbol = 'O';
        int move = game.MiniMax(game.board, game.player2);
        assertEquals(3, move);
    }

    @Test
    void panelInitialization() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        assertEquals(9, panel.getComponentCount());
        for (Component comp : panel.getComponents()) {
            assertTrue(comp instanceof TicTacToeCell);
            assertTrue(comp.isEnabled());
        }
    }

    @Test
    void actionPerformed_ComputerMove() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        TicTacToeCell cell = (TicTacToeCell) panel.getComponent(0);

        ActionEvent event = new ActionEvent(cell, ActionEvent.ACTION_PERFORMED, "");
        panel.actionPerformed(event);

        boolean computerPlayed = false;
        for (Component comp : panel.getComponents()) {
            TicTacToeCell c = (TicTacToeCell) comp;
            if (c.getMarker() == 'O') {
                computerPlayed = true;
                break;
            }
        }
        assertTrue(computerPlayed, "Компьютер должен сделать ответный ход");
    }


}
