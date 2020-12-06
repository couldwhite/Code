package com.codenjoy.dojo.tetris.client;

import com.codenjoy.dojo.services.Command;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.PointImpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RotateZ {
    Board board;
    List<Point> curFreePoints;
    List<Point> freePoint;
    int curY;
    PointImpl curFreePoint;
    PointImpl curPoint;
    public RotateZ(int curY, List<Point> freePoint, Board board) {
        this.curY = curY;
        this.freePoint = freePoint;
        this.board = board;
    }

    private int degree;
    boolean[] flags = new boolean[2];
    int[] num = new int[2];
    PointImpl point1;
    PointImpl point2;
    PointImpl point3;
    //3 точки для поворота на 90
    PointImpl point901;
    PointImpl point902;
    PointImpl point903;
    //2 точки которые должны быть не заняты

    PointImpl badPoint0;
    PointImpl badPoint90;

    public List<Command> rotateZ(List<Point> freePoint, int curY, Board board, int k){
        int i;
        int distance;
        List<Command> result = new ArrayList<Command>();
        curFreePoints = new LinkedList<>();
        for (Point points : freePoint) {
            if (points.getY() == curY) curFreePoints.add(points);
        }
        for(i = k; i < curFreePoints.size(); i++){
            curFreePoint = (PointImpl) curFreePoints.get(i);
            curPoint = (PointImpl) board.getCurrentFigurePoint();
            int curFreeX = curFreePoint.getX();
            int curX = curPoint.getX();
            if(isPossibleRotate(curFreePoints,freePoint,curFreePoint) == 0){
                if(curX > curFreeX){
                    distance = curX - curFreeX - 1;
                    for(int j = 0; j < distance; j ++){
                        result.add(Command.LEFT);
                    }
                    result.add(Command.DOWN);
                }else {
                    distance = curFreeX - curX;
                    for(int j = 0; j < distance; j ++){
                        result.add(Command.RIGHT);
                    }
                    result.add(Command.DOWN);
                }
                return result;
            }else if(isPossibleRotate(curFreePoints,freePoint,curFreePoint) == 90){
                if(curX > curFreeX){
                    result.add(Command.ROTATE_CLOCKWISE_90);
                    distance = curX - curFreeX;
                    for(int j = 0; j < distance; j ++){
                        result.add(Command.LEFT);
                    }
                    result.add(Command.DOWN);
                }else {
                    result.add(Command.ROTATE_CLOCKWISE_90);
                    distance = curFreeX - curX;
                    for(int j = 0; j < distance; j ++){
                        result.add(Command.RIGHT);
                    }
                    result.add(Command.DOWN);
                }
                return result;
            }else if(i < 17){
                return rotateZ(freePoint, curY, board, i+1);
            } else{
                System.out.println("Для данной строки ничего не нашлось, вызываем для строки выше");
                return rotateZ(freePoint, ++curY, board,0);
            }
        }
        return result;
    }
    public int isPossibleRotate(List<Point> curFreePoints, List<Point> freePoint, PointImpl curFreePoint){
        for(int i = 0; i < 2; i++){
            flags[i] = false;
        }
        for(int i = 0; i < 2; i++){
            num[i] = 0;
        }

        point1 = (PointImpl) new PointImpl(curFreePoint.getX() - 1, curFreePoint.getY());
        point2 = (PointImpl) new PointImpl(curFreePoint.getX()-1, curFreePoint.getY() +1);
        point3 = (PointImpl) new PointImpl(curFreePoint.getX() - 2, curFreePoint.getY() + 1);

        if(freePoint.contains(point1) && freePoint.contains(point2) && freePoint.contains(point3)) flags[0] = true;
        badPoint0 = (PointImpl) new PointImpl(curFreePoint.getX()-2 , curFreePoint.getY());
        if (!freePoint.contains(badPoint0)){
            num[0]++;
        }

        point901 = (PointImpl) new PointImpl(curFreePoint.getX(), curFreePoint.getY()+1);
        point902 = (PointImpl) new PointImpl(curFreePoint.getX() + 1, curFreePoint.getY() + 1);
        point903 = (PointImpl) new PointImpl(curFreePoint.getX() + 1, curFreePoint.getY() + 2);

        if(freePoint.contains(point901) && freePoint.contains(point902) && freePoint.contains(point903)) flags[1] = true;
        badPoint90 = (PointImpl) new PointImpl(curFreePoint.getX()+1 , curFreePoint.getY());
        if (!freePoint.contains(badPoint90)){
            num[1]++;
        }


        System.out.println("флаг 0: " + flags[0] +" флаг 90: " + flags[1] + " флаг 180: " );
        if(flags[0]&&num[0]==0){
            degree = 0;
        }else if(flags[1]&&num[1]==0){
            degree = 90;
        }
        else degree=-1;
        return(degree);
    }
}