package by.solbeg.response;

import by.solbeg.enam.Color;

public class Response {

    private int position;
    private Color color;


    public Response(int position, Color color) {
        this.position = position;
        this.color = color;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Response{" +
               "position=" + position +
               ", color=" + color +
               '}';
    }
}
