package com.codenjoy.dojo.tetris.client;

import com.codenjoy.dojo.services.Command;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.PointImpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RotateL {
    Board board;
    List<Point> curFreePoints;
    List<Point> freePoint;
    int curY;
    PointImpl curFreePoint;
    PointImpl curPoint;
    public RotateL(int curY, List<Point> freePoint, Board board) {
        this.curY = curY;
        this.freePoint = freePoint;
        this.board = board;
    }

    private int degree;
    boolean[] flags = new boolean[4];
    int[] num = new int[4];
    PointImpl badPoint01;//если они заняты L не ставим
    PointImpl badPoint02;
    PointImpl point1;
    PointImpl point2;
    PointImpl point3;
    //3 точки для поворота на 90
    PointImpl point901;
    PointImpl point902;
    PointImpl point903;
    //3 точки для поворота на 180
    PointImpl point1801;
    PointImpl point1802;
    PointImpl point1803;
    //2 плохие точки для поворотва на 180
    PointImpl badPoint1801;
    PointImpl badPoint1802;
    //3 точки для поворота на 270
    PointImpl point2701;
    PointImpl point2702;
    PointImpl point2703;
    //2 плохие точки для поворотва на 270
    PointImpl badPoint2701;
    PointImpl badPoint2702;
    public List<Command> rotateL(List<Point> freePoint, int curY, Board board, int k){
        int i;
        int distance;
        boolean flag = false;
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
            if(isPossibleRotate(curFreePoints,freePoint, curFreePoint) == 0){
                if(curX > curFreeX){
                    distance = curX - curFreeX;
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
            } else if(isPossibleRotate(curFreePoints,freePoint, curFreePoint) == 90){
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
            } else if (isPossibleRotate(curFreePoints,freePoint, curFreePoint) == 180){
                if(curX > curFreeX){
                    result.add(Command.ROTATE_CLOCKWISE_180);
                    distance = curX - curFreeX;
                    for(int j = 0; j < distance; j ++){
                        result.add(Command.LEFT);
                    }
                    result.add(Command.DOWN);
                }else {
                    result.add(Command.ROTATE_CLOCKWISE_180);
                    distance = curFreeX - curX;
                    for(int j = 0; j < distance; j ++){
                        result.add(Command.RIGHT);
                    }
                    result.add(Command.DOWN);
                }
                return result;
            } else if (isPossibleRotate(curFreePoints,freePoint, curFreePoint) == 270){
                if(curX > curFreeX){
                    result.add(Command.ROTATE_CLOCKWISE_270);
                    distance = curX - curFreeX+1;
                    for(int j = 0; j < distance; j ++){
                        result.add(Command.LEFT);
                    }
                    result.add(Command.DOWN);
                }else {
                    result.add(Command.ROTATE_CLOCKWISE_270);
                    distance = curFreeX - curX;
                    for(int j = 0; j < distance; j ++){
                        result.add(Command.RIGHT);
                    }
                    result.add(Command.DOWN);
                }
                return result;
            }else if(i < 17){
                return rotateL(freePoint, curY, board,i + 1);
            }else {
                return rotateL(freePoint, curY + 1, board,0);
            }
        }
        if (!flag) {
            return rotateL(freePoint, curY + 1, board, 0);
        }
        return result;
    }
    public int isPossibleRotate(List<Point> curFreePoints,List<Point> freePoint, PointImpl curFreePoint){
        for(int i = 0; i < 4; i++){
            flags[i] = false;
        }
        for(int i = 0; i < 4; i++){
            num[i] = 0;
        }
        point1 = (PointImpl) new PointImpl(curFreePoint.getX(), curFreePoint.getY() + 1);
        point2 = (PointImpl) new PointImpl(curFreePoint.getX(), curFreePoint.getY() + 2);
        point3 = (PointImpl) new PointImpl(curFreePoint.getX() + 1, curFreePoint.getY());

        if(freePoint.contains(point1) && freePoint.contains(point2) && freePoint.contains(point3)) flags[0] = true;
        badPoint01 = (PointImpl) new PointImpl(curFreePoint.getX()+1, curFreePoint.getY()+1);
        badPoint02 = (PointImpl) new PointImpl(curFreePoint.getX()+1, curFreePoint.getY() + 2);
        if (!freePoint.contains(badPoint01)){
            num[0]++;
        }
        if (!freePoint.contains(badPoint02)){
            num[0]++;
        }

        point901 = (PointImpl) new PointImpl(curFreePoint.getX()+1 , curFreePoint.getY());
        point902 = (PointImpl) new PointImpl(curFreePoint.getX() + 2, curFreePoint.getY());
        point903 = (PointImpl) new PointImpl(curFreePoint.getX() + 2, curFreePoint.getY()+1);

        if(freePoint.contains(point901) && freePoint.contains(point902) && freePoint.contains(point903)) flags[1] = true;

        point1801 = (PointImpl) new PointImpl(curFreePoint.getX() , curFreePoint.getY() + 1);
        point1802 = (PointImpl) new PointImpl(curFreePoint.getX(), curFreePoint.getY() + 2);
        point1803 = (PointImpl) new PointImpl(curFreePoint.getX() - 1 , curFreePoint.getY() + 2);
        if(freePoint.contains(point1801) && freePoint.contains(point1802) && freePoint.contains(point1803)) flags[2] = true;

        badPoint1801 = (PointImpl) new PointImpl(curFreePoint.getX()-1, curFreePoint.getY());
        badPoint1802 = (PointImpl) new PointImpl(curFreePoint.getX() -1, curFreePoint.getY() - 1);
        if (freePoint.contains(badPoint1801)){
            num[2]++;
        }
        if (freePoint.contains(badPoint1802)){
            num[2]++;
        }
        point2701 = (PointImpl) new PointImpl(curFreePoint.getX()  , curFreePoint.getY()+1);
        point2702 = (PointImpl) new PointImpl(curFreePoint.getX() + 1, curFreePoint.getY()+1);
        point2703 = (PointImpl) new PointImpl(curFreePoint.getX() +2, curFreePoint.getY() + 1);

        if(freePoint.contains(point2701) && freePoint.contains(point2702) && freePoint.contains(point2703)) flags[3] = true;

        badPoint2701 = (PointImpl) new PointImpl(curFreePoint.getX() + 1, curFreePoint.getY());
        badPoint2702 = (PointImpl) new PointImpl(curFreePoint.getX() +2, curFreePoint.getY());
        if (freePoint.contains(badPoint2701)){
            num[3]++;
        }
        if (freePoint.contains(badPoint2702)){
            num[3]++;

        }
        System.out.println("флаг 0: " + flags[0] +" флаг 90: " + flags[1] + " флаг 180: " + flags[2] + " флаг 270: " + flags[3]);
        if(flags[0]&&num[0]==0){
            degree = 0;
        }else if(flags[1]){
            System.out.println("L на 90");
            degree = 90;
        }else if (flags[2]){
            if (flags[3]){
                if(num[2] <= num [3]){
                    System.out.println("L на 180");
                    degree = 180;
                }else{
                    System.out.println("L на 270");
                    degree = 270;
                }
            }else{
                System.out.println("L на 180");
                degree = 180;
            }
        }else if (flags[3]){
            System.out.println("L на 270");
            degree = 270;
        }
        else degree=-1;
        return (degree);
    }
}
