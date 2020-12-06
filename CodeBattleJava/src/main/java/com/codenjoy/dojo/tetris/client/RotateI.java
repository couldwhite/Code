package com.codenjoy.dojo.tetris.client;

import com.codenjoy.dojo.services.Command;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.PointImpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RotateI {
    Board board;
    List<Point> curFreePoints;
    List<Command> result;
    List<Point> freePoint;
    int curY;
    int[] num = new int[4];
    PointImpl curFreePoint;
    PointImpl curPoint;
    PointImpl point1;
    PointImpl point2;
    PointImpl point3;
    PointImpl point4;
    PointImpl point5;
    PointImpl point6;
    PointImpl badPoint1;
    PointImpl badPoint2;
    PointImpl badPoint3;
    PointImpl badPoint4;
    public RotateI(int curY, List<Point> freePoint, Board board) {
        this.curY = curY;
        this.freePoint = freePoint;
        this.board = board;
    }
    public List<Command> rotateI(List<Point> freePoint, int curY, Board board, int k){
        int i;
        System.out.println("Зашел в rotateO");
        int distance;
        List<Command> result = new ArrayList<Command>();
        curFreePoints = new LinkedList<>();
        boolean flag = false;
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
            if(isPossibleRotate(curFreePoints,freePoint,curFreePoint) == 0){
                if(curX > curFreeX ){
                    distance = curX - curFreeX;
                    for(int j = 0; j < distance; j ++){
                        result.add(Command.LEFT);
                    }
                    result.add(Command.DOWN);
                }else{
                    distance = curFreeX - curX;
                    for(int j = 0; j < distance; j ++){
                        result.add(Command.RIGHT);
                    }
                    result.add(Command.DOWN);
                }
                return result;
            }else if(isPossibleRotate(curFreePoints,freePoint,curFreePoint) == 90){
                result.add(Command.ROTATE_CLOCKWISE_90);
                if(curX > curFreeX ){
                    distance = curX - curFreeX-1;
                    for(int j = 0; j < distance; j ++){
                        result.add(Command.LEFT);
                    }
                    result.add(Command.DOWN);
                }else{
                    distance = curFreeX - curX+1;
                    for(int j = 0; j < distance; j ++){
                        result.add(Command.RIGHT);
                    }
                    result.add(Command.DOWN);
                }
                return result;
            } else if(i < curFreePoints.size()){
                System.out.println("Cейчас для палки i= "+i);
                System.out.println("Свободных ячеек всего: "+curFreePoints.size());
                return rotateI(freePoint, curY, board,i + 1);

            }
            else{
                return rotateI(freePoint, curY + 1, board,0);
            }

        }
        if (!flag) {
            return rotateI(freePoint, curY + 1, board, 0);
        }

        return result;
    }
    //проверяет возможно ли поставить квадрат на этой линии
    public int isPossibleRotate(List<Point> curFreePoints,List<Point> freePoint, PointImpl curFreePoint){
        boolean[] flags = new boolean[2];
        for(int i = 0; i < 2; i++){
            flags[i] = false;
        }
        int degree = -1;
        point1 = (PointImpl) new PointImpl(curFreePoint.getX() - 1, curFreePoint.getY());
        point2 = (PointImpl) new PointImpl(curFreePoint.getX() + 1, curFreePoint.getY());
        point3 = (PointImpl) new PointImpl(curFreePoint.getX() + 2, curFreePoint.getY());

        badPoint1 = (PointImpl) new PointImpl(curFreePoint.getX(), curFreePoint.getY()+1);
        badPoint2 = (PointImpl) new PointImpl(curFreePoint.getX() -1, curFreePoint.getY() +1);
        badPoint3 = (PointImpl) new PointImpl(curFreePoint.getX() +1, curFreePoint.getY() + 1);
        badPoint4 = (PointImpl) new PointImpl(curFreePoint.getX() +1, curFreePoint.getY() + 1);
        if (!freePoint.contains(badPoint1)){
            num[0]++;
        }
        if (!freePoint.contains(badPoint2)){
            num[0]++;
        }
        if (!freePoint.contains(badPoint3)){
            num[0]++;
        }
        if (!freePoint.contains(badPoint4)){
            num[0]++;
        }

        point4 = (PointImpl) new PointImpl(curFreePoint.getX(), curFreePoint.getY() - 1);
        point5 = (PointImpl) new PointImpl(curFreePoint.getX(), curFreePoint.getY() + 1);
        point6 = (PointImpl) new PointImpl(curFreePoint.getX(), curFreePoint.getY() + 2);

        if(freePoint.contains(point1) && freePoint.contains(point2) && freePoint.contains(point3)&&num[0]==0){
            System.out.println("Палку повернуть можно");
            degree = 90;
        }else if(freePoint.contains(point4) && freePoint.contains(point5) && freePoint.contains(point6)){
            System.out.println("Палку поставить можно");
            degree = 0;
        }
        return degree;
    }
}
