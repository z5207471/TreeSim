import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class TreeSim extends PApplet {

Tree trunk;

public void setup() {
  
  frameRate(20);
  
  trunk = new Tree(width/2,height,height/12,PI/2+random(-PI/12,PI/12));
}

public void draw() {
  background(255);
  trunk.draw();
}

public void keyPressed() {
  if (key == ' ') {
    trunk.grow();
  }
  if (key == 'r') {
    trunk = new Tree(width/2,height,height/12,PI/2+random(-PI/12,PI/12));
  }
}
class Branch {
  private float X;
  private float Y;
  private float dX;
  private float dY;
  private float size;
  private float maxSize;
  private float angle;
  private ArrayList<Branch> children;
  private ArrayList<Leaf> leaves;
  private int parents;
  private boolean fullGrown;
  
  Branch(float xPos, float yPos, float s, float a, int p) {
    X = xPos;
    Y = yPos;
    dX = X + size*cos(angle);
    dY = Y - size*sin(angle);
    size = s;
    maxSize = 2*size;
    angle = a;
    children = new ArrayList<Branch>();
    leaves = new ArrayList<Leaf>();
    parents = p;
    fullGrown = false;
  }
  
  public void draw() {
    stroke(115+parents*5, 59+parents*5, 0);
    strokeWeight(size/12);
    line(X,Y,dX,dY);
    for (Leaf l : leaves) {
      l.draw();
    }
    for (Branch b : children) {
      b.draw();
    }
  }
  
  public boolean grow() {
    if (!fullGrown) {
      boolean fGrown = true;
      if (size < maxSize) {
        size += min(0.1f*size, maxSize-size);
        dX = X + size*cos(angle);
        dY = Y - size*sin(angle);
        fGrown = false;
      } else {
        float growChance = random(0,1.0f);
        boolean Grown = children.size() > 3;
        if (parents < 6 && children.size() < 4) {
          if (growChance > 0.95f) {
            growBranch();
          }
          fGrown = false;
        }
        growChance = random(0,1.0f);
        if (leaves.size() < 2*(parents-1)) {
          if (growChance > 0.95f) {
            growLeaf();
          }
          fGrown = false;
        }
        for (Branch b : children) {
          if (!b.grow()) fGrown = false;
        }
      }
      fullGrown = fGrown;
    }
    return fullGrown;
  }
  
  public void growBranch() {
    float scalarPos = random(0.8f,1.0f);
    Branch b = new Branch(X+scalarPos*size*cos(angle), Y-scalarPos*size*sin(angle), random(0.2f,0.4f)*size, angle+random(-PI/4,PI/4), parents+1);
    children.add(b);
  }
  
  public void growLeaf() {
    float scalarPos = random(0.2f,1.0f);
    Leaf l = new Leaf(X+scalarPos*size*cos(angle), Y-scalarPos*size*sin(angle), 10);
    leaves.add(l);
  }
}
class Leaf {
  float x;
  float y;
  float size;
  int Color;
  
  Leaf(float xPos, float yPos, float s) {
    x = xPos;
    y = yPos;
    size = s;
    Color = color(0, 143+random(-20,20), 19, 50);
  }
  
  public void draw() {
    fill(Color);
    noStroke();
    ellipseMode(RADIUS);
    ellipse(x,y,size,size*1.5f);
  }
}
class Tree {
  private float X;
  private float Y;
  private float dX;
  private float dY;
  private float size;
  private float maxSize;
  private float angle;
  private ArrayList<Branch> children;
  private ArrayList<Leaf> leaves;
  private boolean fullGrown;
  
  Tree(float xPos, float yPos, float s, float a) {
    X = xPos;
    Y = yPos;
    dX = X + size*cos(angle);
    dY = Y - size*sin(angle);
    size = s;
    maxSize = 4*size;
    angle = a;
    children = new ArrayList<Branch>();
    leaves = new ArrayList<Leaf>();
    fullGrown = false;
  }
  
  public void draw() {
    if (fullGrown) {
      stroke(255);
    } else {
      stroke(115, 59, 0);
    }
    strokeWeight(size/8);
    line(X,Y,dX,dY);
    for (Leaf l : leaves) {
      l.draw();
    }
    for (Branch b : children) {
      b.draw();
    }
  }
  
  public void grow() {
    if (!fullGrown) {
      boolean fGrown = true;
      if (size < maxSize) {
        size += min(0.1f*size, maxSize-size);
        dX = X + size*cos(angle);
        dY = Y - size*sin(angle);
        fGrown = false;
      } else {
        float growChance = random(0,1.0f);
        if (children.size() < 4) {
          if (growChance > 0.9f) {
            growBranch();
          }
          fGrown = false;
        }
        for (Branch b : children) {
          if (!b.grow()) {
            fGrown = false;
          }
        }
      }
      fullGrown = fGrown;
    }
  }
  
  public void growBranch() {
    float scalarPos = random(0.5f,1.0f);
    Branch b = new Branch(X+scalarPos*size*cos(angle), Y-scalarPos*size*sin(angle), random(0.2f,0.4f)*size, angle+random(-PI/4,PI/4),1);
    children.add(b);
  }
}
  public void settings() {  size(640,480);  pixelDensity(2); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "TreeSim" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
