class Leaf {
  float x;
  float y;
  float size;
  color Color;
  
  Leaf(float xPos, float yPos, float s) {
    x = xPos;
    y = yPos;
    size = s;
    Color = color(0, 143+random(-20,20), 19, 50);
  }
  
  void draw() {
    fill(Color);
    noStroke();
    ellipseMode(RADIUS);
    ellipse(x,y,size,size*1.5);
  }
}
