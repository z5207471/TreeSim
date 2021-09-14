Tree trunk;

void setup() {
  size(640,480);
  frameRate(20);
  pixelDensity(2);
  trunk = new Tree(width/2,height,height/12,PI/2+random(-PI/12,PI/12));
}

void draw() {
  background(255);
  trunk.draw();
}

void keyPressed() {
  if (key == ' ') {
    trunk.grow();
  }
  if (key == 'r') {
    trunk = new Tree(width/2,height,height/12,PI/2+random(-PI/12,PI/12));
  }
}
