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
  
  void draw() {
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
  
  void grow() {
    if (!fullGrown) {
      boolean fGrown = true;
      if (size < maxSize) {
        size += min(0.1*size, maxSize-size);
        dX = X + size*cos(angle);
        dY = Y - size*sin(angle);
        fGrown = false;
      } else {
        float growChance = random(0,1.0);
        if (children.size() < 4) {
          if (growChance > 0.9) {
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
  
  void growBranch() {
    float scalarPos = random(0.5,1.0);
    Branch b = new Branch(X+scalarPos*size*cos(angle), Y-scalarPos*size*sin(angle), random(0.2,0.4)*size, angle+random(-PI/4,PI/4),1);
    children.add(b);
  }
}
