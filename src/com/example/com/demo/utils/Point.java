/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.com.demo.utils;

import android.os.Parcel;
import android.os.Parcelable;


public class Point implements Parcelable {
    public float x;
    public float y;

    public Point() {}

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point src) {
        this.x = src.x;
        this.y = src.y;
    }

    /**
     * Set the pofloat's x and y coordinates
     */
    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public void set(Point src) {
        this.x = src.x;
        this.y = src.y;
    }

    /**
     * Negate the pofloat's coordinates
     */
    public final void negate() {
        x = -x;
        y = -y;
    }

    /**
     * Offset the pofloat's coordinates by dx, dy
     */
    public final void offset(float dx, float dy) {
        x += dx;
        y += dy;
    }

    /**
     * Returns true if the pofloat's coordinates equal (x,y)
     */
    public final boolean equals(float x, float y) {
        return this.x == x && this.y == y;
    }

    @Override public boolean equals(Object o) {
        if (o instanceof Point) {
            Point p = (Point) o;
            return this.x == p.x && this.y == p.y;
        }
        return false;
    }

    @Override 
    public String toString() {
        return "Pofloat(" + x + ", " + y+ ")";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Write this pofloat to the specified parcel. To restore a pofloat from
     * a parcel, use readFromParcel()
     * @param out The parcel to write the pofloat's coordinates floato
     */
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeFloat(x);
        out.writeFloat(y);
    }

    public static final Parcelable.Creator<Point> CREATOR = new Parcelable.Creator<Point>() {
        /**
         * Return a new pofloat from the data in the specified parcel.
         */
        public Point createFromParcel(Parcel in) {
            Point r = new Point();
            r.readFromParcel(in);
            return r;
        }

        /**
         * Return an array of rectangles of the specified size.
         */
        public Point[] newArray(int size) {
            return new Point[size];
        }
    };

    /**
     * Set the pofloat's coordinates from the data stored in the specified
     * parcel. To write a pofloat to a parcel, call writeToParcel().
     *
     * @param in The parcel to read the pofloat's coordinates from
     */
    public void readFromParcel(Parcel in) {
        x = in.readFloat();
        y = in.readFloat();
    }
}
