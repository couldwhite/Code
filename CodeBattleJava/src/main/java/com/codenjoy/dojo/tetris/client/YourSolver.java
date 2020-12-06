package com.codenjoy.dojo.tetris.client;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2016 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import com.codenjoy.dojo.client.AbstractJsonSolver;
import com.codenjoy.dojo.client.WebSocketRunner;
import com.codenjoy.dojo.services.*;

/**
 * User: your name
 * Это твой алгоритм AI для игры. Реализуй его на свое усмотрение.
 * Обрати внимание на {@see YourSolverTest} - там приготовлен тестовый
 * фреймворк для тебя.
 */
public class YourSolver extends AbstractJsonSolver<Board> {

    private Dice dice;

    public YourSolver(Dice dice) {
        this.dice = dice;
    }

    @Override
    public String getAnswer(Board board) {
        List<Command> answerList = getAnswerList(board);
        List<String> stringList = answerList.stream().map(d -> d.toString()).collect(toList());
        return String.join(", ", stringList);
    }

    private List<Command> getAnswerList(Board board) {
        System.out.println("Зашел в дефолтный метод");
        List<Command> result = new ArrayList<Command>();
//лист пустых точек
        List<Point> freePoint = board.getGlass().getFreeSpace();

        List<Point> filledPoint = board.getGlass().getFigures();


//получение самой нижней свободной точки
        int curY;
        curY = freePoint.get(0).getY();
        for (int i = 0; i < freePoint.size(); i++) {
            if (freePoint.get(i).getY() < curY) curY = freePoint.get(i).getY();
        }

//получаем тип фигуры
        String figureType = board.getCurrentFigureType().toString();
        RotateO rotateO;
        RotateJ rotateJ;
        RotateL rotateL;
        RotateS rotateS;
        RotateI rotateI;
        RotateZ rotateZ;
        RotateT rotateT;
        System.out.println("curY " + curY);
        switch (figureType) {
            case "O":
                System.out.println("Квадрат");
                rotateO = new RotateO(curY, freePoint, board);
                result = rotateO.rotateO(freePoint, curY,board,0);
                break;
            case "I":
                System.out.println("J");
                rotateI = new RotateI(curY, freePoint, board);
                result = rotateI.rotateI(freePoint, curY,board,0);
                break;
            case "J":
                System.out.println("J");
                rotateJ = new RotateJ(curY, freePoint, board);
                result = rotateJ.rotateJ(freePoint, curY,board,0);

                break;
            case "L":
                System.out.println("L");
                rotateL = new RotateL(curY, freePoint, board);
                result = rotateL.rotateL(freePoint, curY,board,0);
                break;
            case "S":
                System.out.println("S");
                rotateS = new RotateS(curY, freePoint, board);
                result = rotateS.rotateS(freePoint, curY,board,0);
                break;
            case "Z":
                System.out.println("Z");
                rotateZ = new RotateZ(curY, freePoint, board);
                result = rotateZ.rotateZ(freePoint, curY,board,0);
                break;
            case "T":
                result.add(Command.DOWN);
                break;
        }



        return  result;
    }


    public static void main(String[] args) {
        WebSocketRunner.runClient(
                "http://codebattle2020.westeurope.cloudapp.azure.com/codenjoy-contest/board/player/inzd95jjyrgtqvycqkfn?code=4284612925459180178&gameName=tetris",
                new YourSolver(new RandomDice()),
                new Board());
    }
}