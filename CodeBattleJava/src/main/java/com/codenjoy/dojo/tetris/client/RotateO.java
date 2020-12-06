package com.codenjoy.dojo.tetris.client;

import com.codenjoy.dojo.services.Command;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.PointImpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RotateO {
    Board board;
    List<Point> curFreePoints;
    List<Command> result;
    List<Point> freePoint;
    int curY;
    PointImpl curFreePoint;
    PointImpl curPoint;
    PointImpl point1;
    PointImpl badPoint1;
    PointImpl badPoint2;
    PointImpl badPoint3;
    PointImpl badPoint4;
    PointImpl badPoint5;
    PointImpl badPoint6;
    PointImpl badPoint7;
    PointImpl badPoint8;
    public RotateO(int curY, List<Point> freePoint, Board board) {
        this.curY = curY;
        this.freePoint = freePoint;
        this.board = board;
    }
    public List<Command> rotateO(List<Point> freePoint, int curY, Board board, int k){
        int i;
        System.out.println("Зашел в rotateO");
        int distance;
        boolean flag = false;
        List<Command> result = new ArrayList<Command>();
        curFreePoints = new LinkedList<>();
        for (Point points : freePoint) {
            if (points.getY() == curY) curFreePoints.add(points);
        }
        for(i = k; i < curFreePoints.size(); i++){
            flag=true;
            curFreePoint = (PointImpl) curFreePoints.get(i);
            curPoint = (PointImpl) board.getCurrentFigurePoint();
            int curFreeX = curFreePoint.getX();
            int curX = curPoint.getX();
            System.out.println(curX + " " + curFreeX);
            if(isPossibleRotate(curFreePoints,freePoint,curFreePoint)){
                if(curX > curFreeX ){
                    distance = curX - curFreeX+1;
                    for(int j = 0; j < distance; j ++){
                        result.add(Command.LEFT);
                    }
                    result.add(Command.DOWN);
                }else{
                    distance = curFreeX - curX-1;
                    for(int j = 0; j < distance; j ++){
                        result.add(Command.RIGHT);
                    }
                    result.add(Command.DOWN);
                }
                return result;
            }else if( i < curFreePoints.size()){
                System.out.println("Квадрат рекурсия");
                System.out.println(i);
                return rotateO(freePoint, curY, board, i + 1);
            }
            else{
                return rotateO(freePoint, curY + 1, board, 0);
            }
        }
        if (!flag) {
            return rotateO(freePoint, curY + 1, board, 0);
        }

        return result;
    }
    //проверяет возможно ли поставить квадрат на этой линии
    public boolean isPossibleRotate(List<Point> curFreePoints,List<Point> freePoint, PointImpl curFreePoint){

        System.out.println("Зашел сюда");

        point1 = (PointImpl) new PointImpl(curFreePoint.getX() - 1, curFreePoint.getY());
        badPoint1 = (PointImpl) new PointImpl(curFreePoint.getX(), curFreePoint.getY() + 2);
        badPoint2 = (PointImpl) new PointImpl(curFreePoint.getX(), curFreePoint.getY() + 3);
        badPoint3 = (PointImpl) new PointImpl(curFreePoint.getX() - 1, curFreePoint.getY() + 2);
        badPoint4 = (PointImpl) new PointImpl(curFreePoint.getX() - 1, curFreePoint.getY() + 3);
        badPoint5 = (PointImpl) new PointImpl(curFreePoint.getX() - 1, curFreePoint.getY() - 1);
        badPoint6 = (PointImpl) new PointImpl(curFreePoint.getX(), curFreePoint.getY() - 1);
        badPoint7 = (PointImpl) new PointImpl(curFreePoint.getX() - 1, curFreePoint.getY() + 1);
        badPoint8 = (PointImpl) new PointImpl(curFreePoint.getX(), curFreePoint.getY() + 1);

        if (curFreePoints.contains(point1)
                && freePoint.contains(badPoint1)
                && freePoint.contains(badPoint2)
                && freePoint.contains(badPoint3)
                && freePoint.contains(badPoint4)
                && !freePoint.contains(badPoint5)
                && !freePoint.contains(badPoint6)
                && freePoint.contains(badPoint7)
                && freePoint.contains(badPoint8)) {
            System.out.println("Квадрат поставить можно");
            return true;
        } else {
            return false;
        }

    }
}
