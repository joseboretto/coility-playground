package com.github.joseboretto;

import java.util.ArrayList;
import java.util.List;

public class Battleship {

    public static void main(String[] args)  {
        int N = 4;
        String S = "1B 2C,2D 4D";
        String T = "2B 2D 3D 4D 4A";
        System.out.println(solution(N, S, T));
    }

    public static String solution(int N, String S, String T) {
        // write your code in Java SE 8
        // S = SHIPS
        // T = HITS
        int touched = 0, sunken = 0;
        List<Ship> ships = getShipsFromString(S);
        List<Point> shots = getShotsFromString(T);
        for (Ship ship : ships) {
            ship.calcHitsFromShots(shots);
            int hitsFromShots = ship.calcHitsFromShots(shots);
            if (hitsFromShots > 0) {
                if (hitsFromShots == ship.getSize()) {
                    sunken++;
                } else {
                    touched++;
                }
            }
        }
        return "" + sunken + "," + touched;
    }


    private static List<Ship> getShipsFromString(String S) {
        String[] shipsString = S.split(","); // 1B 2C,2D 4D
        List<Ship> shipList = new ArrayList<>(shipsString.length);
        for (int i = 0; i < shipsString.length; i++) {
            String shipString = shipsString[i]; // 1B 2C
            String[] shipPositionString = shipString.split(" ");
            String shipPositionStart = shipPositionString[0]; // 1B
            String shipPositionEnd = shipPositionString[1]; // 2C
            //
            int shipPositionStartYNumber = getXLetterFromString(shipPositionStart); // B = 2
            int shipPositionStartXLetter = getYNumberFromString(shipPositionStart); // 1 = 1
            Point topLeft = new Point(shipPositionStartXLetter, shipPositionStartYNumber);
            //
            int shipPositionEndYNumber = getXLetterFromString(shipPositionEnd); // C = 3
            int shipPositionEndXLetter = getYNumberFromString(shipPositionEnd); // 2 = 2
            Point bottomRight = new Point(shipPositionEndXLetter, shipPositionEndYNumber);
            Ship ship = new Ship(topLeft, bottomRight);
            shipList.add(ship);
        }
        return shipList;
    }

    private static List<Point> getShotsFromString(String T) {
        String[] shotsString = T.split(" "); // 2B 2D 3D 4A
        List<Point> shotsList = new ArrayList<>(shotsString.length);
        int[][] shots = new int[shotsString.length][2];
        for (int i = 0; i < shotsString.length; i++) {
            String shotString = shotsString[i]; // 2B
            //
            int XLetter = getXLetterFromString(shotString); // B = 2
            int YNumber = getYNumberFromString(shotString); // 2 = 2
            Point point = new Point(XLetter, YNumber);
            shotsList.add(point);
        }
        return shotsList;
    }

    private static int getYNumberFromString(String s) {
        String shipPositionString2 = s.substring(0, 1);
        return Integer.parseInt(shipPositionString2);
    }

    private static int getXLetterFromString(String s) {
        char shipPositionString2 = s.toCharArray()[1];
        int temp = shipPositionString2;
        return temp - 64;
    }

    public static class Point {
        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public boolean greaterOrEqual(Point other) {
            return x >= other.x && y >= other.y;
        }
    }

    public static class Ship {
        private Point topLeft;
        private Point bottomRight;

        public Ship(Point topLeft, Point bottomRight) {
            this.topLeft = topLeft;
            this.bottomRight = bottomRight;
        }

        public int getSize() {
            return (Math.abs(topLeft.getX() - bottomRight.getX()) + 1)
                    * (Math.abs(topLeft.getY() - bottomRight.getY()) + 1);
        }

        public int calcHitsFromShots(List<Point> shots) {
            int hits = 0;
            for (Point shot : shots) {
                if (shot.greaterOrEqual(topLeft) && bottomRight.greaterOrEqual(shot)) {
                    hits++;
                }
            }
            return hits;
        }
    }

}
