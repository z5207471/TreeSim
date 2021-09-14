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
  
  void draw() {
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
  
  boolean grow() {
    if (!fullGrown) {
      boolean fGrown = true;
      if (size < maxSize) {
        size += min(0.1*size, maxSize-size);
        dX = X + size*cos(angle);
        dY = Y - size*sin(angle);
        fGrown = false;
      } else {
        float growChance = random(0,1.0);
        boolean Grown = children.size() > 3;
        if (parents < 6 && children.size() < 4) {
          if (growChance > 0.95) {
            growBranch();
          }
          fGrown = false;
        }
        growChance = random(0,1.0);
        if (leaves.size() < 2*(parents-1)) {
          if (growChance > 0.95) {
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
  
  void growBranch() {
    float scalarPos = random(0.8,1.0);
    Branch b = new Branch(X+scalarPos*size*cos(angle), Y-scalarPos*size*sin(angle), random(0.2,0.4)*size, angle+random(-PI/4,PI/4), parents+1);
    children.add(b);
  }
  
  void growLeaf() {
    float scalarPos = random(0.2,1.0);
    Leaf l = new Leaf(X+scalarPos*size*cos(angle), Y-scalarPos*size*sin(angle), 10);
    leaves.add(l);
  }
}
