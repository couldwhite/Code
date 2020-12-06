package com.codenjoy.dojo.tetris.client;

import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.PointImpl;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class RotateT {
    List<Point> curFreePoints;
    List<Point> freePoint;

    public RotateT(List<Point> curFreePoints, List<Point> freePoint) {
        this.curFreePoints = curFreePoints;
        this.freePoint = freePoint;
    }

    private int degree;
    boolean[] flags = new boolean[4];
    int[] num = new int[4];
    PointImpl point1;
    PointImpl point2;
    PointImpl point3;
    //3 точки для поворота на 90
    PointImpl point901;
    PointImpl point902;
    PointImpl point903;
    //плохая точка для порота на 90
    PointImpl badPoint90;
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
    //2 плохая точка для поворотва на 270
    PointImpl badPoint270;

    public void rotateT(List<Point> curFreePoints, List<Point> freePoint, PointImpl curFreePoint){
        for(int i = 0; i < 4; i++){
            flags[i] = false;
        }
        for(int i = 0; i < 4; i++){
            num[i] = 0;
        }
            point1 = (PointImpl) new PointImpl(curFreePoint.getX() - 1, curFreePoint.getY());
            point2 = (PointImpl) new PointImpl(curFreePoint.getX() - 2, curFreePoint.getY());
            point3 = (PointImpl) new PointImpl(curFreePoint.getX() - 1, curFreePoint.getY() + 1);

            if(freePoint.contains(point1) && freePoint.contains(point2) && freePoint.contains(point3)) flags[0] = true;

            point901 = (PointImpl) new PointImpl(curFreePoint.getX(), curFreePoint.getY() + 1);
            point902 = (PointImpl) new PointImpl(curFreePoint.getX() , curFreePoint.getY() + 2);
            point903 = (PointImpl) new PointImpl(curFreePoint.getX() + 1, curFreePoint.getY() + 1);

            badPoint90 = (PointImpl) new PointImpl(curFreePoint.getX() + 1, curFreePoint.getY());

            if (freePoint.contains(badPoint90)){
                num[1]++;
            }

            if(freePoint.contains(point901) && freePoint.contains(point902) && freePoint.contains(point903)) flags[1] = true;

            point1801 = (PointImpl) new PointImpl(curFreePoint.getX() , curFreePoint.getY() + 1);
            point1802 = (PointImpl) new PointImpl(curFreePoint.getX() - 1, curFreePoint.getY() + 1);
            point1803 = (PointImpl) new PointImpl(curFreePoint.getX() + 1, curFreePoint.getY() +1);

            if(freePoint.contains(point1801) && freePoint.contains(point1802) && freePoint.contains(point1803)) flags[2] = true;

            badPoint1801 = (PointImpl) new PointImpl(curFreePoint.getX() - 1, curFreePoint.getY());
            badPoint1802 = (PointImpl) new PointImpl(curFreePoint.getX() + 1, curFreePoint.getY());

            if (freePoint.contains(badPoint1801)){
                num[2]++;
            }
            if (freePoint.contains(badPoint1802)){
                num[2]++;
            }
            point2701 = (PointImpl) new PointImpl(curFreePoint.getX() , curFreePoint.getY() + 1);
            point2702 = (PointImpl) new PointImpl(curFreePoint.getX(), curFreePoint.getY() + 2);
            point2703 = (PointImpl) new PointImpl(curFreePoint.getX() - 1, curFreePoint.getY() + 1);

            if(freePoint.contains(point2701) && freePoint.contains(point2702) && freePoint.contains(point2703)) flags[3] = true;

            badPoint270 = (PointImpl) new PointImpl(curFreePoint.getX() - 1, curFreePoint.getY());

            if (freePoint.contains(badPoint270)){
                num[3]++;
            }

        System.out.println("флаг 0: " + flags[0] +" флаг 90: " + flags[1] + " флаг 180: " + flags[2] + " флаг 270: " + flags[3]);



        if(flags[0]){
            degree = 0;
        }else if(flags[1]){
            if(flags[2]){
                if(flags[3]){
                    if (num[1] < num[2] && num[1] < num[3]) {
                        degree = 90;
                    }else if (num[1] < num[2] && num[3] < num[1]){
                        degree = 270;
                    } else if(num[2] < num[1] && num[2] < num[3]){
                        degree = 180;
                    } else if(num[3] < num[1] && num[3] < num[2]){
                        degree = 270;
                    } else if (num[1] > num[2] && num[1] > num[3]){
                        degree = 180;
                    } else{
                        degree = 90;
                    }

                }else{
                    if(num[1] > num[2]){
                        degree = 180;
                    }else{
                        degree = 90;
                    }
                }
            }else if(flags[3]){
                if (num[1] > num[3]){
                    degree = 270;
                }else{
                    degree = 90;
                }
            }else{
                degree = 90;
            }
        } else if(flags[2]){
            if(num[2] > num[3]){
                degree = 270;
            }else{
                degree = 180;
            }
        } else if(flags[3]){
            degree = 270;
        } else degree=-1;
        setDegree(degree);
    }

    public int getDegree() {
        return degree;
    }

    public void

    setDegree(int degree) {

        this.degree = degree;
    }

    public boolean[] getFlags() {
        return flags;
    }

    public void setFlags(boolean[] flags) {
        this.flags = flags;
    }

    public int[] getNum() {
        return num;
    }

    public void setNum(int[] num) {
        this.num = num;
    }
}
