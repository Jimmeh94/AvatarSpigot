package avatar.util.misc;

public abstract class Vector<T> {

    protected T x;
    protected T y;
    protected T z;

    public Vector(T one, T two, T three){
        this.x = one;
        this.y = two;
        this.z = three;
    }

    public T getX() {
        return x;
    }

    public T getY() {
        return y;
    }

    public T getZ() {
        return z;
    }

    public void setX(T x) {
        this.x = x;
    }

    public void setY(T y) {
        this.y = y;
    }

    public void setZ(T z) {
        this.z = z;
    }

    public abstract Vector<T> copy();
    public abstract Vector<T> add(Vector vector);
    public abstract Vector<T> subtract(Vector vector);

    public static class Vector3D extends Vector<Double>{

        public Vector3D(Double one, Double two, Double three) {
            super(one, two, three);
        }

        @Override
        public Vector<Double> copy() {
            return new Vector3D(x, y, z);
        }

        @Override
        public Vector<Double> add(Vector vector) {
            x += (Double)vector.getX();
            y += (Double)vector.getY();
            z += (Double)vector.getZ();
            return this;
        }

        @Override
        public Vector<Double> subtract(Vector vector) {
            x -= (Double)vector.getX();
            y -= (Double)vector.getY();
            z -= (Double)vector.getZ();
            return this;
        }
        public double getFloorX(){
            return Math.floor(x);
        }
        public double getFloorY(){
            return Math.floor(y);
        }

        public double getFloorZ(){
            return Math.floor(z);
        }

        public Vector3D reflect(){
            return new Vector3D(x * -1, y * -1, z * -1);
        }

        @Override
        public String toString(){
            return "" + x + " " + y + " " + z;
        }
    }

    public static class Vector3i extends Vector<Integer>{

        public Vector3i(Integer one, Integer two, Integer three) {
            super(one, two, three);
        }

        @Override
        public Vector<Integer> copy() {
            return new Vector3i(x, y, z);
        }

        @Override
        public Vector<Integer> add(Vector vector) {
            x += (Integer)vector.getX();
            y += (Integer)vector.getY();
            z += (Integer)vector.getZ();
            return this;
        }

        @Override
        public Vector<Integer> subtract(Vector vector) {
            x -= (Integer)vector.getX();
            y -= (Integer)vector.getY();
            z -= (Integer)vector.getZ();
            return this;
        }
    }
}
