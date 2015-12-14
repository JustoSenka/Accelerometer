/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.JustInGApps.accelerometer.main;

/**
 *
 * @author JustInG
 */
public class Point3D {
   
    private int id, x, y, z;

    public Point3D(){
        this(0,0,0,0);
    }
    public Point3D(int x, int y, int z){
        this(0,x,y,z);
    }
    public Point3D(int id, int x, int y, int z){
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void setCoords(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }    
    
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        // return "Point3D{" + "id=" + id + ", x=" + x + ", y=" + y + ", z=" + z + '}';
        return "AA " + x + " " + y + " " + z;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.x;
        hash = 53 * hash + this.y;
        hash = 53 * hash + this.z;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Point3D other = (Point3D) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        if (this.z != other.z) {
            return false;
        }
        return true;
    }    
}
