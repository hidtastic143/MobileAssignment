package com.sidm.lab2_week5;

/**
 * Created by HiD on 4/12/2016.
 */

public class vector2 {
    float X;
    float Y;

    vector2()
    {
        X = 0;
        Y = 0;
    }

    vector2(float x, float y)
    {
        X = x;
        Y = y;
    }

    void setVector2(vector2 newPos)
    {
        X = newPos.X;
        Y = newPos.Y;
    }

    void setVector2(float newPosX, float newPosY)
    {
        X = newPosX;
        Y = newPosY;
    }

    float getVector2X()
    {
        return X;
    }

    float getVector2Y()
    {
        return Y;
    }
}
